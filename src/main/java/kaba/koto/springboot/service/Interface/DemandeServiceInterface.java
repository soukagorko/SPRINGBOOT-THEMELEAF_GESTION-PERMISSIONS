package kaba.koto.springboot.service.Interface;

import kaba.koto.springboot.dto.DemandeDTO;
import kaba.koto.springboot.entities.Demande;

import java.util.List;
import java.util.Optional;

public interface DemandeServiceInterface {

    Long createOrUpdateDemande(Demande demande);

    List<Demande> getAllDemandes();

    Long editDemande(Demande demande, Long id);

    void deleteDemande(Long id);

    Demande getOneDemande(Long id);
    //
    Demande getOnePermission(Long id);
    //
    Optional<DemandeDTO> getDemandeById(Long id);


}
