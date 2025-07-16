package com.romit.workouttracker.repositories;

import com.romit.workouttracker.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserId(Integer userId);

    List<Workout> findByUserIdAndWorkoutDateTimeAfter(Integer id, LocalDateTime workoutDateTimeAfter);
}
