package com.romit.workouttracker.DTOs;

public record WorkoutRequestExercise(Long id, String exerciseId, Integer sets, Integer reps, Float weight) {}

