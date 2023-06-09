package com.example.isimmbackendv1.enseignant_matiere;

import com.example.isimmbackendv1.enseignant.Enseignant;
import com.example.isimmbackendv1.enseignant.EnseignantRepository;
import com.example.isimmbackendv1.enseignant_matiere.EnseignantMatiere;
import com.example.isimmbackendv1.enseignant_matiere.EnseignantMatiereRepository;
import com.example.isimmbackendv1.matiere.Matiere;
import com.example.isimmbackendv1.matiere.MatiereRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnseignantMatiereService {

    private final EnseignantMatiereRepository enseignantMatiereRepository;
    private final MatiereRepository matiereRepository;
    private final EnseignantRepository enseignantRepository;

    public EnseignantMatiere getEnseignantMatiere(Long enseignantMatiereId) {
        EnseignantMatiere enseignantMatiere=enseignantMatiereRepository.findById(enseignantMatiereId).orElseThrow(()->new IllegalStateException(("EnseignantMatiere does not exist")));
        return enseignantMatiere;
    }

    public List<EnseignantMatiere> getEnseignantMatieres() {
        return enseignantMatiereRepository.findAll();
    }

    public List<EnseignantMatiere> getEnseignantMatieresByEnseignantId(Long enseignatId) {
        return enseignantMatiereRepository.findEnseignantMatiereByEnseignant(enseignatId);
    }

    public void addEnseignantMatiere(EnseignantMatiere enseignantMatiere) {
        /*Optional<EnseignantMatiere> enseignantMatiereOptional= enseignantMatiereRepository.chargeFindEnseignantMatiereByName(enseignantMatiere.getName());
        if(enseignantMatiereOptional.isPresent()){
            throw new IllegalStateException("EnseignantMatiere Exists");
        }*/
        enseignantMatiereRepository.save(enseignantMatiere);
    }

    public void updateEnseignantMatiere(Long matiereId,Long enseignantId,String type,int nombreGroupes) {
        List<EnseignantMatiere> enseignantMatieres= enseignantMatiereRepository.findEnseignantMatiereByMatiere(matiereId);
        Boolean found = false;
        if(!enseignantMatieres.isEmpty()) {
            System.out.println("Here 2-"+enseignantMatieres.size());
            for (EnseignantMatiere e:enseignantMatieres) {

                if(e.getType().equals(type))
                {

                    found =true;
                    EnseignantMatiere ensMat = enseignantMatiereRepository.findById(e.getEnseignantMatiereId()).orElseThrow(()->new IllegalStateException(("EnseignantMatiere does not exist")));
                    ensMat.setEnseignant(enseignantRepository.findById(enseignantId).orElseThrow(()->new IllegalStateException(("Enseignant does not exist"))));
                    ensMat.setNombreGroupes(nombreGroupes);
                    enseignantMatiereRepository.save(ensMat);

                }
            }
        }
        if(enseignantMatieres.isEmpty() || !found){
                Matiere mat = matiereRepository.findById(matiereId).orElseThrow(()->new IllegalStateException(("Matiere does not exist")));
                Enseignant ens = enseignantRepository.findById(enseignantId).orElseThrow(()->new IllegalStateException(("Enseignant does not exist")));


                EnseignantMatiere newEnsMat = new EnseignantMatiere(mat,ens,type,nombreGroupes);
                enseignantMatiereRepository.save(newEnsMat);

        }
    }


    public void deleteEnseignantMatiere(Long enseignantMatiereId) {
        boolean exists=enseignantMatiereRepository.existsById(enseignantMatiereId);
        if(!exists){
            throw new IllegalStateException("EnseignantMatiere with id"+enseignantMatiereId+" does not exists");
        }
        enseignantMatiereRepository.deleteById(enseignantMatiereId);

    }
}


