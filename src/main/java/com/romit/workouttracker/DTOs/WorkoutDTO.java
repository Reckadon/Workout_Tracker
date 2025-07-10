package com.romit.workouttracker.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutDTO(Long id, LocalDateTime workoutDateTime, List<WorkoutExerciseDTO> exercises) {}

