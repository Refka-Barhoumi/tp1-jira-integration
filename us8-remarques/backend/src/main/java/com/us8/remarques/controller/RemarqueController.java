package com.us8.remarques.controller;

import com.us8.remarques.model.Remarque;
import com.us8.remarques.service.RemarqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/remarques")
@CrossOrigin(origins = "http://localhost:4200")
public class RemarqueController {

    @Autowired
    private RemarqueService remarqueService;

    // GET http://localhost:8080/remarques/1
    @GetMapping("/{etudiantId}")
    public ResponseEntity<List<Remarque>> getRemarques(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(remarqueService.getRemarquesByEtudiant(etudiantId));
    }

    // POST http://localhost:8080/remarques
    // Body: { "contenu": "...", "etudiantId": 1 }
    @PostMapping
    public ResponseEntity<Remarque> ajouterRemarque(@RequestBody Remarque remarque) {
        return ResponseEntity.ok(remarqueService.ajouterRemarque(remarque));
    }
}
