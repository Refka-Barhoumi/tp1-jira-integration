package jury_backend.repository;

import jury_backend.model.Jury;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JuryRepository extends JpaRepository<Jury, Long> {
    // This magic line lets us find a Jury just by typing their email!
    Optional<Jury> findByEmail(String email);
}