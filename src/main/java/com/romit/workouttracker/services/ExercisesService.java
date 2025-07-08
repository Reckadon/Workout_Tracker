package com.romit.workouttracker.services;

import com.romit.workouttracker.projections.ExerciseSlim;
import com.romit.workouttracker.repositories.ExerciseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercisesService {
    private final ExerciseRepository exerciseRepository;

    public ExercisesService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Cacheable(value = "exercisesList", key = "#primaryMuscle == null ? 'ALL' : #primaryMuscle")
    public List<ExerciseSlim> getExercises(String primaryMuscle) {
        if (primaryMuscle != null && !primaryMuscle.isEmpty()) {
            return exerciseRepository.findExerciseByPrimaryMusclesContains(primaryMuscle);
        }
        return exerciseRepository.findAllProjectedBy();
    }
}
