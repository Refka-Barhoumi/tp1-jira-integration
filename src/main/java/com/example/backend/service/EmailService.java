package com.example.backend.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void envoyerRemarque(String emailEtudiant, String nomEtudiant,
                                 String nomTuteur, String contenu) {
        // Email désactivé temporairement
        System.out.println("Email simulé à : " + emailEtudiant);
        System.out.println("Remarque : " + contenu);
    }
}