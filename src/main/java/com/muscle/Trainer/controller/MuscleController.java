package com.muscle.Trainer.controller;

import com.muscle.Trainer.dtos.ExerciseDTO;
import com.muscle.Trainer.dtos.MuscleDTO;
import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.repositories.MuscleRepository;
import com.muscle.Trainer.service.ExerciseService;
import com.muscle.Trainer.service.MuscleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/muscles")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class MuscleController {



    private final MuscleService muscleService;
    private final MuscleRepository muscleRepository;
    private final ExerciseService exerciseService;


    @GetMapping
    public List<MuscleDTO> getALLMuscles(){
        return muscleService.getAllMuscles();
    }
    @GetMapping("/{name}")
    public MuscleDTO getMuscle(@PathVariable String name){
        return muscleService.getMuscleDto(name);
    }

    @GetMapping("/{id}/exercises")
    public List<ExerciseDTO> getExercisesByMuscle(
            @PathVariable Long id) {

        return exerciseService.getExercisesByMuscle(id);
    }

}
