package com.romit.workouttracker.services;

import com.romit.workouttracker.DTOs.WorkoutDTO;
import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.entities.Workout;
import com.romit.workouttracker.repositories.UserRepository;
import com.romit.workouttracker.repositories.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final UserRepository usersRepo;
    private final WorkoutRepository workoutRepo;

    public WorkoutService(UserRepository usersRepo, WorkoutRepository workoutRepo) {
        this.usersRepo = usersRepo;
        this.workoutRepo = workoutRepo;
    }

    public void saveWorkout(String username, WorkoutDTO dto) {
        Users user = usersRepo.findByUsername(username);
        Workout w = new Workout();
        w.setWorkoutDateTime(dto.workoutDate());
        w.setUser(user);

//        dto.exercises().forEach(e -> {
//            WorkoutExercise we = new WorkoutExercise();
//            we.setExerciseId(e.exerciseId());
//            we.setSets(e.sets());
//            we.setReps(e.reps());
//            we.setWeight(e.weight());
//            w.addExercise(we);          // sets both sides
//        });

        workoutRepo.save(w);
    }

    public List<Workout> getWorkouts(String username) {
        Users user = usersRepo.findByUsername(username);
        return workoutRepo.findByUserId(user.getId());
    }
}
