package com.example.backend.repository;

import com.example.backend.model.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TuteurRepository extends JpaRepository<Tuteur, Long> {
    Optional<Tuteur> findByEmail(String email);
}