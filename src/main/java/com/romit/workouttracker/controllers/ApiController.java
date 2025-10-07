package com.romit.workouttracker.controllers;

import com.romit.workouttracker.DTOs.AnalysisResultDTO;
import com.romit.workouttracker.DTOs.LogWorkoutRequest;
import com.romit.workouttracker.DTOs.MetricsDTO;
import com.romit.workouttracker.clients.DataAnalysisGRPCClient;
import com.romit.workouttracker.clients.PythonServiceClient;
import com.romit.workouttracker.projections.ExerciseSlim;
import com.romit.workouttracker.services.ExercisesService;
import com.romit.workouttracker.services.JWTService;
import com.romit.workouttracker.services.WorkoutService;
import com.romit.workouttracker.proto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ExercisesService exercisesService;
    private final JWTService jwt;
    private final WorkoutService workoutService;
//    private final PythonServiceClient pythonServiceClient;
    private final DataAnalysisGRPCClient dataAnalysisGRPCClient;

    public ApiController(ExercisesService exercisesService, JWTService jwt, WorkoutService workoutService, PythonServiceClient pythonServiceClient, DataAnalysisGRPCClient dataAnalysisGRPCClient) {
        this.exercisesService = exercisesService;
        this.jwt = jwt;
        this.workoutService = workoutService;
//        this.pythonServiceClient = pythonServiceClient;
        this.dataAnalysisGRPCClient = dataAnalysisGRPCClient;
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
    public ResponseEntity<?> log(@RequestBody LogWorkoutRequest dto,
                                 @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwt.extractUsername(token);
        workoutService.saveWorkout(username, dto);
        return ResponseEntity.ok("logged");
    }

    @GetMapping("/workout/analysis")
    public AnalysisResultDTO getWorkoutLog(@RequestHeader("Authorization") String auth)  {
        String token = auth.substring(7);
        String username = jwt.extractUsername(token);
        // convert past week workouts' raw data to proto format
        PastWeekWorkouts pastWeekWorkouts = PastWeekWorkouts.newBuilder()
            .addAllWorkouts(workoutService.getPastWeekWorkouts(username).stream().map(dto ->
                WorkoutData.newBuilder()
                    .setWorkoutId(Math.toIntExact(dto.id()))
                    .setDate(dto.workoutDateTime().toString())
                    .addAllExercises(dto.exercises().stream().map(exercise ->
                            WorkoutExercise.newBuilder()
                                    .setPrimaryMuscleGroup(exercise.primaryMuscles())
                                    .setName(exercise.exerciseName())
                                    .setSets(exercise.sets())
                                    .setReps(exercise.reps())
                                    .setWeight(exercise.weight())
                                    .build()
                    ).toList())
                    .build()
            ).toList())
            .build();

        // get proto response from gRPC service
        AnalysisResult res = dataAnalysisGRPCClient.callPastWeekAnalysis(pastWeekWorkouts);
        // convert to DTO
        return new AnalysisResultDTO(res.getStatus(), new MetricsDTO(res.getResults().getCount(), res.getResults().getSevenDayVolumeHeatmapList(), res.getResults().getMuscleWiseVolumeMap()));
    }
}
