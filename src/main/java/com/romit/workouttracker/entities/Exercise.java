package com.romit.workouttracker.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Exercise {
    @Id
    private String id;

    private String name;
    private String force;
    private String level;
    private String mechanic;
    private String equipment;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String primaryMuscles;

    @Column(columnDefinition = "TEXT")
    private String secondaryMuscles;

    @Column(columnDefinition = "TEXT")
    private String instructions;
}
