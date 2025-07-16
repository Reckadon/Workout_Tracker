package com.romit.workouttracker.clients;

import com.romit.workouttracker.DTOs.WorkoutDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PythonServiceClient {
    private final WebClient webClient;

    public PythonServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String callPastWeekAnalysis(List<WorkoutDTO> pastWeekWorkouts)  {
//        System.out.println("Calling Python service for past week analysis with " + pastWeekWorkouts.size() + " workouts.");
        return webClient.post()
                .uri("/api/analysis/pastWeekData")
                .header("Content-Type", "application/json")
                .bodyValue(pastWeekWorkouts)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
