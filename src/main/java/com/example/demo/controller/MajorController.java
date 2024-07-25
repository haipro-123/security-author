package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nganhHoc")
public class MajorController {
    @GetMapping("")
    public String index() {
        return "Hi World";
    }
}
