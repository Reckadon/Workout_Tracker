package com.romit.workouttracker.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Users {
    @Id
    private Integer id;
    private String username;
    private String password;
}
