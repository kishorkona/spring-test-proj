package com.test.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionSeedController {

    @GetMapping("/seed-session")
    public ResponseEntity<String> seed(HttpSession session) {
        session.setAttribute("principalName", "demoUser");
        return ResponseEntity.ok("Session seeded: " + session.getId());
    }
}
