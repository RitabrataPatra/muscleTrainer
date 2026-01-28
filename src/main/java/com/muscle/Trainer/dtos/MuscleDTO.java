package com.muscle.Trainer.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MuscleDTO {
    private  Long id;
    private String name;
    private List<ExerciseDTO> exercises;
}
