package com.muscle.Trainer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private Long id;
    private String name;
    private int sets;
    private int reps;
    private String equipment;
    private String howToPerform;

}
