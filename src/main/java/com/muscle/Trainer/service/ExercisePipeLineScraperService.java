package com.muscle.Trainer.service;


import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.repositories.MuscleRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
@AllArgsConstructor
public class ExercisePipeLineScraperService {
    private final MuscleRepository muscleRepository;
    private final ScraperService exerciseScraper; //
    private String buildMusclePageUrl(String muscleName) {
        return "https://www.muscleandstrength.com/exercises/" +
                muscleName.toLowerCase().replace(" ", "-");
    }
    private List<String> scrapeExerciseUrlsForMuscle(String muscleName)
            throws IOException {

        String muscleUrl = buildMusclePageUrl(muscleName);

        Document doc = Jsoup.connect(muscleUrl)
                .userAgent("Mozilla/5.0")
                .timeout(15_000)
                .get();

        return doc
                .select("div.featured-taxonomy-content a[href^=/exercises/][href$=.html]")
                .stream()
                .map(a -> "https://www.muscleandstrength.com" + a.attr("href"))
                .distinct()
                .toList();
    }

    public String scrapeAllExercisesForAllMuscles() {

        List<Muscle> muscles = muscleRepository.findAll();
        int totalExercises = 0;

        for (Muscle muscle : muscles) {
            try {
                List<String> urls = scrapeExerciseUrlsForMuscle(muscle.getName());

                for (String url : urls) {
                    try {
                        exerciseScraper.scrapeExerciseProfile(url);
                        totalExercises++;
                        Thread.sleep(1200); // rate limit
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
