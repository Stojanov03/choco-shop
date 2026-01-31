package com.choco.chocoshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    private String email;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDateTime registrationDate = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private Role role = Role.BUYER;
}
