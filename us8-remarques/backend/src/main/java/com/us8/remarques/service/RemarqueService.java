package com.us8.remarques.service;

import com.us8.remarques.model.Remarque;
import com.us8.remarques.repository.RemarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RemarqueService {

    @Autowired
    private RemarqueRepository remarqueRepository;

    public List<Remarque> getRemarquesByEtudiant(Long etudiantId) {
        return remarqueRepository.findByEtudiantId(etudiantId);
    }

    public Remarque ajouterRemarque(Remarque remarque) {
        return remarqueRepository.save(remarque);
    }
}
