package com.romit.workouttracker.services;

import com.romit.workouttracker.entities.Exercise;
import com.romit.workouttracker.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercisesService {
    private final ExerciseRepository exerciseRepository;

    public ExercisesService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getExercises(String primaryMuscle) {
        if (primaryMuscle != null && !primaryMuscle.isEmpty()) {
            return exerciseRepository.findExerciseByPrimaryMusclesContains(primaryMuscle);
        }
        return exerciseRepository.findAll();
    }
}
