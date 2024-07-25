package com.example.demo.repository;

import com.example.demo.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserRepository extends JpaRepository<RoleUser, Integer> {
}
