package com.romit.workouttracker.repositories;

import com.romit.workouttracker.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExerciseRepository extends JpaRepository<Exercise, String> {

    List<Exercise> findExerciseByLevelAndPrimaryMusclesContains(String level, String primaryMuscle);

    List<Exercise> findExerciseByPrimaryMusclesContains(String primaryMuscle);
}
