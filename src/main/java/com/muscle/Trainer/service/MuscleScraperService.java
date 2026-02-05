package com.muscle.Trainer.service;

import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.repositories.MuscleRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MuscleScraperService {

    private final MuscleRepository muscleRepository;

    private static final String MUSCLE_LIST_URL =
            "https://www.muscleandstrength.com/exercises";

    public String scrapeAndSaveAllMuscles() {

        try {
            Document doc = Jsoup.connect(MUSCLE_LIST_URL)
                    .userAgent("Mozilla/5.0")
                    .timeout(15000)
                    .get();

            Elements muscles = doc.select("div.cell a:has(.category-name)");


            int savedCount = 0;
            for (Element el : muscles) {

                String name = el.select(".category-name").text();

                String url = el.absUrl("href"); // 🔥 handles .html automatically

                Element cell = el.closest("div.cell");
                Element img = cell.selectFirst("img");
                String imageUrl = img != null
                        ? (img.hasAttr("data-src") ? img.absUrl("data-src") : img.absUrl("src"))
                        : null;
                Muscle muscle = muscleRepository.findBySourceUrl(url);

                if (muscle == null) {

                    muscle = new Muscle();
                    muscle.setName(name);
                    muscle.setSourceUrl(url);
                    muscle.setImageUrl(imageUrl);

                    muscleRepository.save(muscle);
                    savedCount++;

                } else if (muscle.getImageUrl() == null && imageUrl != null) {

                    muscle.setImageUrl(imageUrl);
                    muscleRepository.save(muscle);
                }
//                if (!muscleRepository.existsBySourceUrl(url)) {
//
//                    Muscle muscle = new Muscle();
//                    muscle.setName(name);
//                    muscle.setSourceUrl(url);
//                    muscle.setImageUrl(imageUrl);
//
//                    muscleRepository.save(muscle);
//
//                    System.out.println("Saved muscle -> " + name);
//
//                    savedCount++;
//                }

            }

            return "Muscle scraping completed. Saved: " + savedCount;

        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape muscle list", e);
        }
    }
}
