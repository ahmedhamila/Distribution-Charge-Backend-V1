package com.example.isimmbackendv1.enseignant;

import com.example.isimmbackendv1.enseignant_matiere.EnseignantMatiere;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;

    public Enseignant getEnseignant(Long enseignantId) {
        Enseignant enseignant=enseignantRepository.findById(enseignantId).orElseThrow(()->new IllegalStateException(("Enseignant does not exist")));
        return enseignant;
    }
    public Optional<Enseignant> getEnseignantByName(String nom, String prenom) {
        return enseignantRepository.findEnseignantByName(nom,prenom);

    }

    public Enseignant getEnseignantByName(String enseignantName) {
        Enseignant enseignant=enseignantRepository.chargeFindEnseignantByName(enseignantName).orElseThrow(()->new IllegalStateException(("Enseignant does not exist")));
        return enseignant;
    }

    public List<Enseignant> getEnseignants() {
        return enseignantRepository.findAll();
    }

    public void addEnseignant(Enseignant enseignant) {
        Optional<Enseignant> enseignantOptional= enseignantRepository.chargeFindEnseignantByName(enseignant.getNom());
        if(enseignantOptional.isPresent()){
            throw new IllegalStateException("Enseignant Exists");
        }
        enseignantRepository.save(enseignant);
    }
    public void deleteEnseignant(Long enseignantId) {
        boolean exists=enseignantRepository.existsById(enseignantId);
        if(!exists){
            throw new IllegalStateException("Enseignant with id"+enseignantId+" does not exists");
        }
        enseignantRepository.deleteById(enseignantId);

    }
    public void updateEnseignant(Long enseignantId, String nom, String prenom , String email)
    {
        Enseignant ens = enseignantRepository.findById(enseignantId).orElseThrow(()->new IllegalStateException(("EnseignantMatiere does not exist")));
        ens.setNom(nom);
        ens.setPrenom(prenom);
        ens.setEmail(email);
        enseignantRepository.save(ens);
    }

    public void updateEnseignantPassword(Long enseignantId, String oldPass, String newPass)
    {
        Enseignant ens = enseignantRepository.findById(enseignantId).orElseThrow(()->new IllegalStateException(("EnseignantMatiere does not exist")));
        if(ens.getPassword().equals(oldPass)){
            ens.setPassword(newPass);
        }
        enseignantRepository.save(ens);
    }
}


