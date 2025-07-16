package com.romit.workouttracker.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseDTO {
    public String id;
    public String name;
    public String force;
    public String level;
    public String mechanic;
    public String equipment;
    public String category;
    public List<String> primaryMuscles;
    public List<String> secondaryMuscles;
    public List<String> instructions;
}

