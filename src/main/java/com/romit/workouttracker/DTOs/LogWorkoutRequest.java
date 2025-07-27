package com.romit.workouttracker.DTOs;

import java.time.LocalDateTime;
import java.util.List;
public record LogWorkoutRequest(Long id, LocalDateTime workoutDateTime, List<WorkoutRequestExercise> exercises) {}


