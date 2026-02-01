package com.muscle.Trainer.repositories;

import com.muscle.Trainer.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ExerciseRepository extends JpaRepository<Exercise , Long> {
    List<Exercise> findByMuscleId(Long muscleId);

    boolean existsBySourceUrl(String sourceUrl);

    @Query("SELECT e.sourceUrl FROM Exercise e")
    Set<String> findAllSourceUrls();
}
