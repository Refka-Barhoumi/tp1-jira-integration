package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Etudiant;
import com.example.backend.model.Tuteur;
import com.example.backend.repository.EtudiantRepository;
import com.example.backend.repository.TuteurRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final EtudiantRepository etudiantRepository;

    @Autowired
    private TuteurRepository tuteurRepository;

    public AuthController(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Backend OK !");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String motDePasse = body.get("motDePasse");
        Optional<Etudiant> opt = etudiantRepository.findByEmail(email);
        if (opt.isPresent() && opt.get().getMotDePasse() != null
                && opt.get().getMotDePasse().equals(motDePasse)) {
            Etudiant e = opt.get();
            Map<String, Object> res = new HashMap<>();
            res.put("id", e.getId());
            res.put("nom", e.getNom());
            res.put("prenom", e.getPrenom());
            res.put("email", e.getEmail());
            res.put("role", "etudiant");
            res.put("message", "Connexion reussie");
            return ResponseEntity.ok(res);
        }
        Map<String, String> err = new HashMap<>();
        err.put("erreur", "Email ou mot de passe incorrect");
        return ResponseEntity.status(401).body(err);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String nouveauMotDePasse = body.get("nouveauMotDePasse");

        // Chercher dans étudiant
        Optional<Etudiant> etudiantOpt = etudiantRepository.findByEmail(email);
        if (etudiantOpt.isPresent()) {
            Etudiant e = etudiantOpt.get();
            e.setMotDePasse(nouveauMotDePasse);
            etudiantRepository.save(e);
            Map<String, String> res = new HashMap<>();
            res.put("message", "Mot de passe modifié avec succès");
            return ResponseEntity.ok(res);
        }

        // Chercher dans tuteur
        Optional<Tuteur> tuteurOpt = tuteurRepository.findByEmail(email);
        if (tuteurOpt.isPresent()) {
            Tuteur t = tuteurOpt.get();
            t.setMotDePasse(nouveauMotDePasse);
            tuteurRepository.save(t);
            Map<String, String> res = new HashMap<>();
            res.put("message", "Mot de passe modifié avec succès");
            return ResponseEntity.ok(res);
        }

        Map<String, String> err = new HashMap<>();
        err.put("erreur", "Email introuvable");
        return ResponseEntity.status(404).body(err);
    }
}