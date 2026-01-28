package com.muscle.Trainer.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Muscle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(
            mappedBy = "muscle",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Exercise> exercises = new ArrayList<>();

    // ✅ Helper method (VERY IMPORTANT)
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        exercise.setMuscle(this);

    }
}