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

    @Cacheable(value = "exercisesList", key = "#search == null ? 'ALL' : #search")
    public List<ExerciseSlim> getExercises(String search) {
        if (search != null && !search.isEmpty()) {
            return exerciseRepository.findExerciseByNameContainsIgnoreCase(search);
        }
        return exerciseRepository.findAllProjectedBy();
    }

    public ExerciseSlim getExerciseById(String id) {
        return exerciseRepository.findExerciseById(id);
    }
}
