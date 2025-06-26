package com.romit.workouttracker.services;

import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public Users registerUser(Users user) throws IllegalArgumentException{
        if (isUsernameAvailable(user.getUsername())) {
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Username is already taken");
    }
}
