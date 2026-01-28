package com.muscle.Trainer.service;

import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.repositories.MuscleRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
                    .timeout(15_000)
                    .get();

            // 🔥 Core selector
            Elements muscleElements = doc.select("div.category-name");

            int savedCount = 0;

            for (var element : muscleElements) {
                String muscleName = element.text().trim();

                if (muscleName.isEmpty()) continue;

                // Avoid duplicates
                if (muscleRepository.existsByNameIgnoreCase(muscleName)) {
                    continue;
                }

                Muscle muscle = new Muscle();
                muscle.setName(muscleName);

                muscleRepository.save(muscle);
                savedCount++;
            }

            return "Muscle scraping completed. Saved: " + savedCount;

        } catch (IOException e) {
            throw new RuntimeException("Failed to scrape muscle list", e);
        }
    }
}
