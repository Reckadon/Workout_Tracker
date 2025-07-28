package com.romit.workouttracker.mappers;

import com.romit.workouttracker.DTOs.LogWorkoutRequest;
import com.romit.workouttracker.DTOs.WorkoutDTO;
import com.romit.workouttracker.DTOs.WorkoutExerciseDTO;
import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.entities.Workout;
import com.romit.workouttracker.entities.WorkoutExercise;
import com.romit.workouttracker.projections.ExerciseSlim;
import com.romit.workouttracker.services.ExercisesService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
/* WorkoutMapper.java
* This class can be used to map between different representations of workouts
* For example, it can convert entities to DTOs or vice versa.
*/
public class WorkoutMapper {
    private final ExercisesService exercisesService;

    public WorkoutMapper(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    public Workout toEntity(LogWorkoutRequest dto, Users user) {
        Workout workout = new Workout();
        workout.setWorkoutDateTime(dto.workoutDateTime());
        workout.setUser(user);

        List<WorkoutExercise> exercises = dto.exercises().stream()
                .map(exDto -> {
                    WorkoutExercise ex = new WorkoutExercise();
                    ex.setExerciseId(exDto.exerciseId());
                    ex.setSets(exDto.sets());
                    ex.setReps(exDto.reps());
                    ex.setWeight(exDto.weight());
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
                        .map(ex -> {
                            ExerciseSlim exerciseData = exercisesService.getExerciseById(ex.getExerciseId());
                            return new WorkoutExerciseDTO(exerciseData.getName(), exerciseData.getPrimaryMuscles(), ex.getSets(), ex.getReps(), ex.getWeight());
                        })
                        .toList()
        );
    }
}
