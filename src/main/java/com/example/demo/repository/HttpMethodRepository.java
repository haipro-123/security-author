package com.example.demo.repository;

import com.example.demo.entity.HttpMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpMethodRepository extends JpaRepository<HttpMethod, Integer> {
}
