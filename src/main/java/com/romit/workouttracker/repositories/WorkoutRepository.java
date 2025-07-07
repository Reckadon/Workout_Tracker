package com.romit.workouttracker.repositories;

import com.romit.workouttracker.DTOs.WorkoutDTO;
import com.romit.workouttracker.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<WorkoutDTO> findByUserId(Integer userId);
}
