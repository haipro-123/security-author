package com.example.demo.repository;

import com.example.demo.entity.OtpUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OtpUserRepository extends JpaRepository<OtpUser, UUID> {
}
