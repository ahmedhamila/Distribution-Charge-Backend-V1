package com.example.isimmbackendv1.diplome;

import com.example.isimmbackendv1.diplome.Diplome;
import com.example.isimmbackendv1.diplome.DiplomeRepository;
import com.example.isimmbackendv1.matiere.MatiereDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiplomeService {

    private final DiplomeRepository diplomeRepository;

    public Diplome getDiplome(Long diplomeId) {
        Diplome diplome=diplomeRepository.findById(diplomeId).orElseThrow(()->new IllegalStateException(("Diplome does not exist")));
        return diplome;
    }
    public Diplome getDiplomeName(String diplomeName) {
        Diplome diplome=diplomeRepository.chargeFindDiplomeByName(diplomeName).orElseThrow(()->new IllegalStateException(("Diplome does not exist")));
        return diplome;
    }

    public List<Diplome> getDiplomes() {
        return diplomeRepository.findAll();
    }


    public void addDiplome(Diplome diplome) {
        Optional<Diplome> diplomeOptional= diplomeRepository.chargeFindDiplomeByName(diplome.getName());
        if(diplomeOptional.isPresent()){
            throw new IllegalStateException("Diplome Exists");
        }
        diplomeRepository.save(diplome);
    }


    public void deleteDiplome(Long diplomeId) {
        boolean exists=diplomeRepository.existsById(diplomeId);
        if(!exists){
            throw new IllegalStateException("Diplome with id"+diplomeId+" does not exists");
        }
        diplomeRepository.deleteById(diplomeId);

    }
}

