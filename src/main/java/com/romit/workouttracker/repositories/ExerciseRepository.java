package com.romit.workouttracker.repositories;

import com.romit.workouttracker.entities.Exercise;
import com.romit.workouttracker.projections.ExerciseSlim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExerciseRepository extends JpaRepository<Exercise, String> {

//    List<ExerciseSlim> findExerciseByPrimaryMusclesContains(String primaryMuscle);

    List<ExerciseSlim> findAllProjectedBy();

    List<ExerciseSlim> findExerciseByNameContainsIgnoreCase(String search);

    ExerciseSlim findExerciseById(String id);
}
