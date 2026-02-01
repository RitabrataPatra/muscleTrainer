package com.muscle.Trainer.service;

import com.muscle.Trainer.models.Exercise;
import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.repositories.ExerciseRepository;
import com.muscle.Trainer.repositories.MuscleRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ScraperService {
    private final MuscleRepository muscleRepository;
    private final ExerciseRepository exerciseRepository;

    public String scrapeExerciseProfile(String url) {

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(15_000)
                    .get();

            // 1️⃣ Get exercise name (usually page title or h1)
            String exerciseName = doc.selectFirst("h1").text();

            // 2️⃣ Locate Exercise Profile block
            Elements rows = doc.select("div.node-stats-block ul li");

            Map<String, String> profileData = new HashMap<>();

            for (Element row : rows) {
                String label = row.selectFirst("span.row-label").text().trim();
                String value = row.select("div.field-items").text().trim();

                profileData.put(label, value);
            }

            // 3️⃣ Extract fields
            String targetMuscle = profileData.get("Target Muscle Group");
            String equipment = profileData.get("Equipment Required");
//            String experience = profileData.get("Experience Level");
//            String secondaryMuscles = profileData.get("Secondary Muscles");

            // 3️⃣ INSTRUCTIONS (ordered list)
            List<String> instructionSteps = doc
                    .select("div.field-name-body ol li")
                    .eachText();

            String instructions = IntStream.range(0, instructionSteps.size())
                    .mapToObj(i -> (i + 1) + ". " + instructionSteps.get(i))
                    .collect(Collectors.joining("\n"));

            // 4️⃣ TIPS (handle li or p)
            List<String> tipsList = doc
                    .select("div.field-name-field-exercise-tips li, div.field-name-field-exercise-tips p")
                    .eachText();

            String tips = tipsList.stream()
                    .map(t -> "• " + t)
                    .collect(Collectors.joining("\n"));

            // 4️⃣ Save Muscle
            Muscle muscle = muscleRepository
                    .findByNameIgnoreCase(targetMuscle)
                    .orElseGet(() -> {
                        Muscle m = new Muscle();
                        m.setName(targetMuscle);
                        return muscleRepository.save(m);
                    });

            // 5️⃣ Save Exercise
            Exercise exercise = new Exercise();
            exercise.setName(exerciseName);
            exercise.setEquipment(equipment);
            exercise.setHowToPerform(instructions);
            exercise.setSets(3);
            exercise.setReps(12);
            exercise.setSourceUrl(url);
//            exercise.setExperienceLevel(experience);
//            exercise.setSecondaryMuscles(secondaryMuscles);
//            exercise.setMuscle(muscle);
            muscle.addExercise(exercise);
            exerciseRepository.save(exercise);

            return "Scraped and saved: " + exerciseName;

        } catch (IOException e) {
            throw new RuntimeException("Scraping failed", e);
        }
    }
}
