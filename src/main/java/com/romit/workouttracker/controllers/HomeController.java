package com.romit.workouttracker.controllers;

import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.services.JWTService;
import com.romit.workouttracker.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController {
    private final UsersService usersService;
    private final JWTService jwtService;

    public HomeController(UsersService usersService, JWTService jwtService) {
        this.usersService = usersService;
        this.jwtService = jwtService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Workout Tracker API! Use /api/exercises to access exercise data.";
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return usersService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        System.out.println("Login attempt for user: " + user.getUsername() + " with password: " + user.getPassword());
        if (usersService.isUsernameAvailable(user.getUsername())) {   // if username is available, it means user does not exist
            System.out.println("Username not found: " + user.getUsername());
            return ResponseEntity.notFound().build();
        }

        if (usersService.verifyUser(user)) {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(token);
        }
        return null;
    }
}
