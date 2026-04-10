package jury_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "soutenance")
public class Soutenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateSoutenance;

    @ManyToOne
    @JoinColumn(name = "jury_id")
    private Jury jury;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    // --- AJOUTE CES 4 LIGNES ICI ---
    private String rapportUrl;
    private Double note;
    private String commentaire;
    private String statut;

    // --- TES ANCIENS GETTERS/SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateSoutenance() { return dateSoutenance; }
    public void setDateSoutenance(LocalDateTime dateSoutenance) { this.dateSoutenance = dateSoutenance; }
    public Jury getJury() { return jury; }
    public void setJury(Jury jury) { this.jury = jury; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }

    // --- AJOUTE LES NOUVEAUX GETTERS/SETTERS ICI ---
    public String getRapportUrl() { return rapportUrl; }
    public void setRapportUrl(String rapportUrl) { this.rapportUrl = rapportUrl; }
    
    public Double getNote() { return note; }
    public void setNote(Double note) { this.note = note; }
    
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}