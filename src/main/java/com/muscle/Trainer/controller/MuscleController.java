package com.muscle.Trainer.controller;

import com.muscle.Trainer.dtos.MuscleDTO;
import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.service.MuscleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/muscles")
@AllArgsConstructor
public class MuscleController {

    private final MuscleService muscleService;
    @GetMapping("/{name}")
    public MuscleDTO getMuscle(@PathVariable String name){
        return muscleService.getMuscleDto(name);
    }
}
