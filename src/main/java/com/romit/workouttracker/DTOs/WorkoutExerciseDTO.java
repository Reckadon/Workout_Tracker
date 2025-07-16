package com.romit.workouttracker.DTOs;

public record WorkoutExerciseDTO (Long id, String exerciseName, Integer sets, Integer reps, Float weight) { }
