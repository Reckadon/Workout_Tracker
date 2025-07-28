package com.romit.workouttracker.DTOs;

public record WorkoutExerciseDTO (String exerciseName, String primaryMuscles,  Integer sets, Integer reps, Float weight) { }
