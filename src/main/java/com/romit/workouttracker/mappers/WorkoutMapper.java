package com.romit.workouttracker.mappers;

import com.romit.workouttracker.DTOs.WorkoutDTO;
import com.romit.workouttracker.DTOs.WorkoutExerciseDTO;
import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.entities.Workout;
import com.romit.workouttracker.entities.WorkoutExercise;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
/* WorkoutMapper.java
* This class can be used to map between different representations of workouts
* For example, it can convert entities to DTOs or vice versa.
*/
public class WorkoutMapper {

    public Workout toEntity(WorkoutDTO dto, Users user) {
        Workout workout = new Workout();
        workout.setWorkoutDateTime(dto.workoutDateTime());
        workout.setUser(user);

        List<WorkoutExercise> exercises = dto.exercises().stream()
                .map(exDto -> {
                    WorkoutExercise ex = new WorkoutExercise();
                    ex.setExerciseId(exDto.exerciseId());
                    ex.setSets(exDto.sets());
                    ex.setReps(exDto.reps());
                    ex.setWorkout(workout); // Important!
                    return ex;
                })
                .toList();

        workout.setExercises(exercises);
        return workout;
    }

    public WorkoutDTO toDTO(Workout workout) {
        return new WorkoutDTO(
                workout.getId(),
                workout.getWorkoutDateTime(),
                workout.getExercises().stream()
                        .map(ex -> new WorkoutExerciseDTO(ex.getId(), ex.getExerciseId(), ex.getSets(), ex.getReps()))
                        .toList()
        );
    }
}
