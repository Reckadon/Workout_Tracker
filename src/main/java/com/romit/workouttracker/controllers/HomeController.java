package com.romit.workouttracker.controllers;

import com.romit.workouttracker.entities.Users;
import com.romit.workouttracker.services.JWTService;
import com.romit.workouttracker.services.UsersService;
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
    public String login(@RequestBody Users user) {
        System.out.println("Attempting login for user: " + user.getUsername() + " with password: " + user.getPassword());
        if (usersService.isUsernameAvailable(user.getUsername())) {
            return "Login failed: Username not found.";
        }
        return usersService.verifyUser(user) ? jwtService.generateToken(user) : "Login failed: Incorrect password.";
    }
}
