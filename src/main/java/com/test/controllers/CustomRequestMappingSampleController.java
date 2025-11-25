package com.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomRequestMappingSampleController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }
}

