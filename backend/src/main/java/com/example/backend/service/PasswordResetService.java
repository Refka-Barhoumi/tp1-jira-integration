package com.example.backend.service;

import com.example.backend.entity.Student;
import com.example.backend.repository.StudentRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final StudentRepository studentRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(StudentRepository studentRepository,
                                 JavaMailSender mailSender,
                                 PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    // Demande de réinitialisation — envoyer email
    public void requestPasswordReset(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aucun compte trouvé avec cet email"));

        // Générer token unique
        String token = UUID.randomUUID().toString();
        student.setResetToken(token);
        student.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        studentRepository.save(student);

        // Envoyer email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Réinitialisation de mot de passe");
        message.setText("Cliquez sur ce lien pour réinitialiser votre mot de passe:\n\n"
                + "http://localhost:4200/reset-password?token=" + token
                + "\n\nCe lien expire dans 1 heure.");
        mailSender.send(message);
    }

    // Vérifier token et mettre à jour le mot de passe
    public void resetPassword(String token, String newPassword) {
        Student student = studentRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        // Vérifier expiration
        if (student.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expiré");
        }

        // Mettre à jour mot de passe
        student.setPassword(passwordEncoder.encode(newPassword));
        student.setResetToken(null);
        student.setResetTokenExpiry(null);
        studentRepository.save(student);
    }
}