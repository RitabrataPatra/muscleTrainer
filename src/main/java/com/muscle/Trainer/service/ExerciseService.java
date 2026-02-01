package com.muscle.Trainer.service;

import com.muscle.Trainer.dtos.ExerciseDTO;
import com.muscle.Trainer.repositories.ExerciseRepository;
import com.muscle.Trainer.repositories.MuscleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;

    public List<ExerciseDTO> getExercisesByMuscle(Long muscleId) {
        if (!muscleRepository.existsById(muscleId)) {
            throw new RuntimeException("Muscle not found");
        }

        return exerciseRepository
                .findByMuscleId(muscleId)
                .stream()
                .map(e -> new ExerciseDTO(
                        e.getId(),
                        e.getName(),
                        e.getSets(),
                        e.getReps(),
                        e.getEquipment(),
                        e.getHowToPerform()
                ))
                .toList();
    }
}
