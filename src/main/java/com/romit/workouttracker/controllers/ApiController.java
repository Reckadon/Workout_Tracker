package com.romit.workouttracker.controllers;

import com.romit.workouttracker.DTOs.WorkoutDTO;
import com.romit.workouttracker.clients.PythonServiceClient;
import com.romit.workouttracker.projections.ExerciseSlim;
import com.romit.workouttracker.services.ExercisesService;
import com.romit.workouttracker.services.JWTService;
import com.romit.workouttracker.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ExercisesService exercisesService;
    private final JWTService jwt;
    private final WorkoutService workoutService;
    private final PythonServiceClient pythonServiceClient;

    public ApiController(ExercisesService exercisesService, JWTService jwt, WorkoutService workoutService, PythonServiceClient pythonServiceClient) {
        this.exercisesService = exercisesService;
        this.jwt = jwt;
        this.workoutService = workoutService;
        this.pythonServiceClient = pythonServiceClient;
    }

    @GetMapping("/exercises")
    public List<ExerciseSlim> exercises(@RequestParam(required = false) String search) {
        return exercisesService.getExercises(search);
    }

    @GetMapping("/workouts")
    public ResponseEntity<?> getWorkouts(@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwt.extractUsername(token);
        return ResponseEntity.ok(workoutService.getWorkouts(username));
    }

    @PostMapping("/workout/log")
    public ResponseEntity<?> log(@RequestBody WorkoutDTO dto,
                                 @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwt.extractUsername(token);
        workoutService.saveWorkout(username, dto);
        return ResponseEntity.ok("logged");
    }

    @GetMapping("/workout/analysis")
    public String getWorkoutLog(@RequestHeader("Authorization") String auth)  {
        String token = auth.substring(7);
        String username = jwt.extractUsername(token);
        return pythonServiceClient.callPastWeekAnalysis(workoutService.getPastWeekWorkouts(username));
    }
}
