package jury_backend.controller;

import jury_backend.model.Soutenance;
import jury_backend.repository.SoutenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController // Tells Spring this is a Web Controller
@RequestMapping("/api/jury") // The base URL: http://localhost:8080/api/jury
@CrossOrigin(origins = "http://localhost:4200") // Allows Angular to talk to Java
public class JuryController {

    @Autowired
    private SoutenanceRepository soutenanceRepository;

    // This method handles: GET /api/jury/{email}
    @GetMapping("/{email}")
    public List<Soutenance> getSoutenancesByEmail(@PathVariable String email) {
        // We use our "magic" repository method here
        return soutenanceRepository.findByJuryEmail(email);
    }

// handles: PUT /api/soutenance/{id}
    @PutMapping("/soutenance/{id}")
    public Soutenance updateSoutenance(@PathVariable Long id, @RequestBody Soutenance updatedSoutenance) {
        return soutenanceRepository.findById(id)
            .map(soutenance -> {
                soutenance.setNote(updatedSoutenance.getNote());
                soutenance.setStatut(updatedSoutenance.getStatut());
                return soutenanceRepository.save(soutenance);
            })
            .orElseThrow(() -> new RuntimeException("Soutenance introuvable avec l'id : " + id));
    }
    @PutMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> payload) {
    String email = payload.get("email");
    String newPassword = payload.get("newPassword");

    // Ici, on simule la recherche de l'utilisateur et la mise à jour
    // Dans un vrai projet, tu aurais une table "Jury" avec un champ "password"
    System.out.println("Réinitialisation du mot de passe pour : " + email);
    System.out.println("Nouveau mot de passe : " + newPassword);

    return "Mot de passe mis à jour avec succès !";
    }
}




    

