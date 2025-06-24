package com.romit.workouttracker.dataloaders;

import com.fasterxml.jackson.core.type.TypeReference;  //Jackson is a high-performance, open-source JSON processor library for Java.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romit.workouttracker.DTOs.ExerciseDTO;
import com.romit.workouttracker.entities.Exercise;
import com.romit.workouttracker.repositories.ExerciseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ExerciseDataLoader implements CommandLineRunner {

    private final ExerciseRepository repository;
    private final ObjectMapper objectMapper;

    public ExerciseDataLoader(ExerciseRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/exercises.json");
        if (inputStream == null) {
            throw new IllegalArgumentException("exercises.json not found in classpath");
        }
        TypeReference<List<ExerciseDTO>> typeRef = new TypeReference<>() {};
        List<ExerciseDTO> dtoList = objectMapper.readValue(inputStream, typeRef);

        List<Exercise> exercises = dtoList.stream().map(dto -> {
            Exercise e = new Exercise();
            e.setId(dto.id);
            e.setName(dto.name);
            e.setForce(dto.force);
            e.setLevel(dto.level);
            e.setMechanic(dto.mechanic);
            e.setEquipment(dto.equipment);
            e.setCategory(dto.category);
            e.setPrimaryMuscles(String.join(",", dto.primaryMuscles));
            e.setSecondaryMuscles(String.join(",", dto.secondaryMuscles));
            e.setInstructions(String.join("\n", dto.instructions));
            return e;
        }).toList();

        repository.saveAll(exercises);
        System.out.println("Loaded " + exercises.size() + " exercises from exercises.json");
    }
}

