package com.muscle.Trainer.repositories;

import com.muscle.Trainer.models.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuscleRepository extends JpaRepository<Muscle , Long> {
    Optional<Muscle> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

    boolean existsBySourceUrl(String sourceUrl);

}
