package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "remarque")
public class Remarque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId;

    @Column(name = "tuteur_id")
    private Long tuteurId;

    @Column(nullable = false)
    private String statut = "Non lue";

    @Column(nullable = false)
    private boolean lu = false;

    @Column(name = "fichier_nom")
    private String fichierNom;

    @Column(name = "fichier_chemin")
    private String fichierChemin;

    public Remarque() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }

    public Long getTuteurId() { return tuteurId; }
    public void setTuteurId(Long tuteurId) { this.tuteurId = tuteurId; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public boolean isLu() { return lu; }
    public void setLu(boolean lu) { this.lu = lu; }

    public String getFichierNom() { return fichierNom; }
    public void setFichierNom(String fichierNom) { this.fichierNom = fichierNom; }

    public String getFichierChemin() { return fichierChemin; }
    public void setFichierChemin(String fichierChemin) { this.fichierChemin = fichierChemin; }
}
