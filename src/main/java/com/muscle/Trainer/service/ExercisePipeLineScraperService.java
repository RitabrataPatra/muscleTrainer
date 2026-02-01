package com.muscle.Trainer.service;


import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.repositories.ExerciseRepository;
import com.muscle.Trainer.repositories.MuscleRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ExercisePipeLineScraperService {
    private final MuscleRepository muscleRepository;
    private final ExerciseRepository exerciseRepository;
    private final ScraperService exerciseScraper; //
//    private String buildMusclePageUrl(String muscleName) {
//        return "https://www.muscleandstrength.com/exercises/" +
//                muscleName.toLowerCase().replace(" ", "-");
//    }

    private List<String> scrapeExerciseUrlsForMuscle(String muscleUrl)
            throws IOException {

        Document doc = Jsoup.connect(muscleUrl)
                .userAgent("Mozilla/5.0")
                .timeout(15_000)
                .get();

        return doc
                .select("div.featured-taxonomy-content a[href^=/exercises/]")
                .stream()
//                .map(a -> "https://www.muscleandstrength.com" + a.attr("href"))
                .map(a -> a.absUrl("href"))
                .distinct()
                .toList();
    }

    public String scrapeAllExercisesForAllMuscles() {

        List<Muscle> muscles = muscleRepository.findAll();
        int totalExercises = 0;
        Set<String> existingUrls = exerciseRepository.findAllSourceUrls();

        for (Muscle muscle : muscles) {

            try {

                List<String> urls = scrapeExerciseUrlsForMuscle(muscle.getSourceUrl());

                for (String url : urls) {

                    if (existingUrls.contains(url)) {
                        System.out.println("Duplicate skipped: " + url);
                        continue;
                    }

                    try {

                        exerciseScraper.scrapeExerciseProfile(url);

                        totalExercises++;
                        existingUrls.add(url);

                        Thread.sleep(1200);

                    } catch (DataIntegrityViolationException e) {

                        System.out.println("Duplicate blocked by DB: " + url);

                    } catch (Exception e) {

                        System.out.println("Failed exercise: " + url);
                    }
                }

            } catch (Exception e) {

                System.out.println("Failed muscle: " + muscle.getName());
            }
        }


        return "Scraping completed. Total exercises processed: " + totalExercises;
    }


}
