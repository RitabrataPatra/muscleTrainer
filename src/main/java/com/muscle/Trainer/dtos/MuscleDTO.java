package com.muscle.Trainer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MuscleDTO {
    private  Long id;
    private String name;
    private String imageUrl;
}
