package com.romit.workouttracker.controllers;

import com.romit.workouttracker.entities.Exercise;
import com.romit.workouttracker.services.ExercisesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ExercisesService exercisesService;

    public ApiController(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    @GetMapping("/exercises")
    public List<Exercise> exercises(@RequestParam(required = false) String primaryMuscle) {
        return exercisesService.getExercises(primaryMuscle);
    }
}
