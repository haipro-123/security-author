package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "http_method")
@NoArgsConstructor @AllArgsConstructor @Getter
public class HttpMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Getter
    @Column(name = "http_method_name")
    private String httpMethodName;
}
