package com.us8.remarques.repository;

import com.us8.remarques.model.Remarque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RemarqueRepository extends JpaRepository<Remarque, Long> {
    List<Remarque> findByEtudiantId(Long etudiantId);
}
