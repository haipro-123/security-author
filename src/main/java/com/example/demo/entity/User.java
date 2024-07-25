package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "_user")
@NoArgsConstructor @AllArgsConstructor @Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Setter
    @Column(name = "email")
    private String email;
    @Setter
    @Column(name = "password")
    private String password;
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleUser role;
}
