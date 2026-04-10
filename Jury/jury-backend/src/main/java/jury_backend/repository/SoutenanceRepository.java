package jury_backend.repository;

import jury_backend.model.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {
    // This finds all defenses (soutenances) for a specific Jury email
    List<Soutenance> findByJuryEmail(String email);
}