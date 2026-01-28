package com.muscle.Trainer.controller;

import com.muscle.Trainer.service.ExercisePipeLineScraperService;
import com.muscle.Trainer.service.MuscleScraperService;
import com.muscle.Trainer.service.ScraperService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminScrapeController {

    private final ScraperService scraperService;
    private final MuscleScraperService muscleScraperService;
    private final ExercisePipeLineScraperService exercisePipeLineScraperService;

    @PostMapping("/ping")
    public String ping() {
        return "admin ok";
    }

    @PostMapping("/scrape/exercise")
    public ResponseEntity<String> scrapeChest(@RequestParam String url) {
        return ResponseEntity.ok(scraperService.scrapeExerciseProfile(url));
    }
    @PostMapping("/scrape/muscles")
    public ResponseEntity<String> scrapeMuscles() {
        return ResponseEntity.ok(
                muscleScraperService.scrapeAndSaveAllMuscles()
        );
    }
    @PostMapping("/scrape/all-exercises")
    public ResponseEntity<String> scrapeAllExercises() {
        return ResponseEntity.ok(
                exercisePipeLineScraperService.scrapeAllExercisesForAllMuscles()
        );
    }
}
