package com.romit.workouttracker.DTOs;

public record WorkoutExerciseDTO (Long id, String exerciseId, Integer sets, Integer reps, Float weight) { }
