package com.romit.workouttracker.controllers;

import com.romit.workouttracker.entities.Exercise;
import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.services.ExercisesService;
import com.romit.workouttracker.services.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {
    private final ExercisesService exercisesService;
    private final UsersService usersService;

    public HomeController(ExercisesService exercisesService, UsersService usersService) {
        this.exercisesService = exercisesService;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Workout Tracker API! Use /exercises to access exercise data.";
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return usersService.registerUser(user);
    }

    @GetMapping("/exercises")
    public List<Exercise> exercises(@RequestParam(required = false) String primaryMuscle) {
        return exercisesService.getExercises(primaryMuscle);
    }
}
