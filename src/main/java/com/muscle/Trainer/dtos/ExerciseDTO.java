package com.muscle.Trainer.dtos;

import lombok.Data;

@Data
public class ExerciseDTO {
    private Long id;
    private String name;
    private int sets;
    private int reps;
    private String equipment;
    private String howToPerform;

}
