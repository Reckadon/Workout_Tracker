package com.romit.workouttracker.controllers;

import com.romit.workouttracker.entities.Exercise;
import com.romit.workouttracker.services.ExercisesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    private final ExercisesService exercisesService;

    public HomeController(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Workout Tracker API! Use /exercises to access exercise data.";
    }

    @GetMapping("/exercises")
    public List<Exercise> exercises(@RequestParam(required = false) String primaryMuscle) {
        return exercisesService.getExercises(primaryMuscle);
    }
}
