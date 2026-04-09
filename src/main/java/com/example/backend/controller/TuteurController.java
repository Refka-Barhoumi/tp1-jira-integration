package com.example.backend.controller;

import com.example.backend.model.Etudiant;
import com.example.backend.model.Remarque;
import com.example.backend.model.Tuteur;
import com.example.backend.repository.EtudiantRepository;
import com.example.backend.repository.RemarqueRepository;
import com.example.backend.repository.TuteurRepository;
import com.example.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tuteur")
@CrossOrigin(origins = "*")
public class TuteurController {

    @Autowired
    private TuteurRepository tuteurRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private RemarqueRepository remarqueRepository;

    @Autowired
    private EmailService emailService;

    private final String uploadDir = "uploads/";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String motDePasse = body.get("motDePasse");

        Optional<Tuteur> opt = tuteurRepository.findByEmail(email);

        if (opt.isPresent() && opt.get().getMotDePasse().equals(motDePasse)) {
            Tuteur t = opt.get();
            Map<String, Object> res = new HashMap<>();
            res.put("id", t.getId());
            res.put("nom", t.getNom());
            res.put("prenom", t.getPrenom());
            res.put("email", t.getEmail());
            res.put("role", "TUTEUR");
            res.put("message", "Connexion réussie");
            return ResponseEntity.ok(res);
        }

        Map<String, String> err = new HashMap<>();
        err.put("erreur", "Email ou mot de passe incorrect");
        return ResponseEntity.status(401).body(err);
    }

    @GetMapping("/etudiants")
    public ResponseEntity<List<Etudiant>> getEtudiants() {
        return ResponseEntity.ok(etudiantRepository.findAll());
    }

    @PostMapping(value = "/remarque", consumes = "multipart/form-data")
    public ResponseEntity<?> envoyerRemarque(
            @RequestParam("contenu") String contenu,
            @RequestParam("etudiantId") Long etudiantId,
            @RequestParam("tuteurId") Long tuteurId,
            @RequestParam(value = "fichier", required = false) MultipartFile fichier) {

        try {
            Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
            Optional<Tuteur> tuteurOpt = tuteurRepository.findById(tuteurId);

            if (etudiantOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("erreur", "Étudiant introuvable"));
            }

            Etudiant etudiant = etudiantOpt.get();
            Tuteur tuteur = tuteurOpt.get();

            Remarque r = new Remarque();
            r.setContenu(contenu);
            r.setEtudiantId(etudiantId);
            r.setTuteurId(tuteurId);
            r.setDate(LocalDateTime.now());
            r.setStatut("Non lue");

            if (fichier != null && !fichier.isEmpty()) {
                Files.createDirectories(Paths.get(uploadDir));
                String nomFichier = System.currentTimeMillis() + "_" + fichier.getOriginalFilename();
                Path chemin = Paths.get(uploadDir + nomFichier);
                Files.copy(fichier.getInputStream(), chemin, StandardCopyOption.REPLACE_EXISTING);
                r.setFichierNom(fichier.getOriginalFilename());
                r.setFichierChemin(nomFichier);
            }

            remarqueRepository.save(r);

            emailService.envoyerRemarque(
                etudiant.getEmail(),
                etudiant.getPrenom() + " " + etudiant.getNom(),
                tuteur.getPrenom() + " " + tuteur.getNom(),
                contenu
            );

            Map<String, String> res = new HashMap<>();
            res.put("message", "Remarque envoyée avec succès !");
            return ResponseEntity.status(201).body(res);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("erreur", "Erreur fichier : " + e.getMessage()));
        }
    }

    @GetMapping("/remarques/{tuteurId}")
    public ResponseEntity<List<Remarque>> getRemarquesTuteur(@PathVariable Long tuteurId) {
        return ResponseEntity.ok(remarqueRepository.findByTuteurId(tuteurId));
    }
}