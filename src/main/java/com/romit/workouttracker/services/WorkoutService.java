package com.romit.workouttracker.services;

import com.romit.workouttracker.DTOs.WorkoutDTO;
import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.mappers.WorkoutMapper;
import com.romit.workouttracker.repositories.UserRepository;
import com.romit.workouttracker.repositories.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final UserRepository usersRepo;
    private final WorkoutRepository workoutRepo;
    private final WorkoutMapper workoutMapper;

    public WorkoutService(UserRepository usersRepo, WorkoutRepository workoutRepo, WorkoutMapper workoutMapper) {
        this.usersRepo = usersRepo;
        this.workoutRepo = workoutRepo;
        this.workoutMapper = workoutMapper;
    }

    public void saveWorkout(String username, WorkoutDTO dto) {
        Users user = usersRepo.findByUsername(username);
        workoutRepo.save(workoutMapper.toEntity(dto, user));
    }

    public List<WorkoutDTO> getWorkouts(String username) {
        Users user = usersRepo.findByUsername(username);
        return workoutRepo.findByUserId(user.getId()).stream().map(workoutMapper::toDTO).toList();
    }
}
