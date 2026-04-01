package com.example.backend.controller;

import com.example.backend.entity.Student;
import com.example.backend.repository.StudentRepository;
import com.example.backend.security.JwtUtil;
import com.example.backend.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final PasswordResetService passwordResetService;

    public AuthController(StudentRepository studentRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          PasswordResetService passwordResetService) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.passwordResetService = passwordResetService;
    }

    // Inscription
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student student) {
        // Validation format email institutionnel
        if (!student.getEmail().matches("^[\\w.]+@[\\w-]+\\.[\\w-]+\\.tn$") &&
            !student.getEmail().matches("^[\\w.]+@[\\w-]+\\.[\\w-]+\\.[\\w-]+\\.tn$")) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Email institutionnel requis"));
        }

        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Email déjà utilisé"));
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return ResponseEntity.ok(Map.of("message", "Inscription réussie"));
    }

    // Connexion
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        return studentRepository.findByEmail(email)
                .filter(s -> passwordEncoder.matches(password, s.getPassword()))
                .map(s -> ResponseEntity.ok(Map.of(
                        "token", jwtUtil.generateToken(email),
                        "studentId", s.getId(),
                        "fullName", s.getFullName()
                )))
                .orElse(ResponseEntity.status(401)
                        .body(Map.of("error", "Email ou mot de passe incorrect")));
    }

    // US4 — Demande de réinitialisation du mot de passe
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        try {
            passwordResetService.requestPasswordReset(body.get("email"));
            return ResponseEntity.ok(Map.of("message", "Email de réinitialisation envoyé"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // US4 — Réinitialisation du mot de passe avec token
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        try {
            passwordResetService.resetPassword(body.get("token"), body.get("newPassword"));
            return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}