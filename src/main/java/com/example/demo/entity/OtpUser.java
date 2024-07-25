package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "otp_user")
@NoArgsConstructor @AllArgsConstructor @Getter
public class OtpUser {
    @Id
    @Column(name = "id_user")
    private UUID idUser;
    @Setter
    @Column(name = "otp")
    private String otp;
}
