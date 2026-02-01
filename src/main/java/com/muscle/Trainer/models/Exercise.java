package com.muscle.Trainer.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int sets;
    private int reps;
    private String equipment;
    @Column(length = 2000)
    private String howToPerform;

    @Column(unique = true)
    private String sourceUrl;

//    @Column(length = 2000)
//    private String posture;

    @ManyToOne
    @JoinColumn(name = "muscle_id")
    private Muscle muscle;
}
