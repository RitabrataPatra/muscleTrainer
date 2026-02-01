package com.muscle.Trainer.service;

import com.muscle.Trainer.dtos.ExerciseDTO;
import com.muscle.Trainer.dtos.MuscleDTO;
import com.muscle.Trainer.models.Muscle;
import com.muscle.Trainer.repositories.MuscleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MuscleService {

    private MuscleRepository muscleRepository;
    public Optional<Muscle> getMuscleByName(String name) {
        return muscleRepository.findByNameIgnoreCase(name);
    }

    public List<MuscleDTO> getAllMuscles() {

        return muscleRepository.findAll()
                .stream()
                .map(muscle ->
                        new MuscleDTO(
                                muscle.getId(),
                                muscle.getName()
                        )
                )
                .toList();
    }

    public MuscleDTO getMuscleDto(String name) {

        Muscle muscle = muscleRepository
                .findByNameIgnoreCase(name)
                .orElseThrow();

        MuscleDTO dto = new MuscleDTO();
        dto.setId(muscle.getId());
        dto.setName(muscle.getName());

        List<ExerciseDTO> exercises = muscle.getExercises()
                .stream()
                .map(e -> {
                    ExerciseDTO ex = new ExerciseDTO();
                    ex.setId(e.getId());
                    ex.setName(e.getName());
                    ex.setEquipment(e.getEquipment());
                    ex.setSets(e.getSets());
                    ex.setReps(e.getReps());
                    ex.setHowToPerform(e.getHowToPerform());
                    return ex;
                })
                .toList();

//        dto.setExercises(exercises);
        return dto;
    }

}

