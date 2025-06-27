package com.romit.workouttracker.services;

import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UsersService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public Users registerUser(Users user) throws IllegalArgumentException {
        if (isUsernameAvailable(user.getUsername())) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Username is already taken");
    }

    public boolean verifyUser(Users user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );
        return authentication.isAuthenticated();
    }
}
