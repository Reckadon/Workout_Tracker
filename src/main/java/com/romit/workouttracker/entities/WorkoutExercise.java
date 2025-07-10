package com.romit.workouttracker.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exerciseId;
    private Integer sets;
    private Integer reps;

    //    Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;
}
