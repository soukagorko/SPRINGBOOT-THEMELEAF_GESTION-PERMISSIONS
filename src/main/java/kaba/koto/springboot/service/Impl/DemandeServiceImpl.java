package kaba.koto.springboot.service.Impl;

import kaba.koto.springboot.dto.DemandeDTO;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.service.Interface.DemandeServiceInterface;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandeServiceImpl implements DemandeServiceInterface {

    private final DemandeRepository demandeRepository;

    @Override
    public Long createOrUpdateDemande(Demande demande) {
//        return this.demandeRepository.save(demande).getIdDemande();
        return this.demandeRepository.save(demande).getId();
    }

    @Override
    public List<Demande> getAllDemandes() {
        return List.of();
    }

    @Override
    public Long editDemande(Demande demande, Long id) {
        Optional<Demande> demandeAMettreAjourOptional = null;

        demandeAMettreAjourOptional = this.demandeRepository.findById(id);

        if (demandeAMettreAjourOptional.isPresent()) {

            Demande demandeAMettreAjour = demandeAMettreAjourOptional.get();

            demandeAMettreAjour.setUser(demande.getUser());
            demandeAMettreAjour.setService(demande.getService());
            demandeAMettreAjour.setPersonnel(demande.getPersonnel());
            demandeAMettreAjour.setService(demande.getService());
            demandeAMettreAjour.setDestinataireDemande(demande.getDestinataireDemande());
            demandeAMettreAjour.setDureePermission(demande.getDureePermission());
            demandeAMettreAjour.setLieu(demande.getLieu());
            demandeAMettreAjour.setDateDebut(demande.getDateDebut());
            demandeAMettreAjour.setObjet(demande.getObjet());
            demandeAMettreAjour.setMotif(demande.getMotif());
            demandeAMettreAjour.setDestination(demande.getDestination());
            demandeAMettreAjour.setDateFin(demande.getDateFin());
//            return this.demandeRepository.save(demandeAMettreAjour).getIdDemande();
            return this.demandeRepository.save(demandeAMettreAjour).getId();
        } else {
            return Long.valueOf(0);
        }
    }

    @Override
    public void deleteDemande(Long id) {
        this.demandeRepository.deleteById(id);
    }

    @Override
    public Demande getOneDemande(Long id) {
        Optional<Demande> demandeOptional = this.demandeRepository.findById(id);

        if (demandeOptional.isEmpty()){
            return null;
        }
        return demandeOptional.get();
    }

    @Override
    public Demande getOnePermission(Long id) {
        //
        Optional<Demande> permissionOptional = this.demandeRepository.findById(id);
        if (permissionOptional.isEmpty()){
            return null;
        }
        return permissionOptional.get();
        //
    }

    @Override
    public Optional<DemandeDTO> getDemandeById(Long id) {
        // Simuler la récupération de la demande depuis la base de données
        DemandeDTO demande = new DemandeDTO();
        demande.setId(id);
        demande.setUser("Utilisateur Exemple");
        demande.setPersonnel("Personnel Exemple");
        demande.setService("Service Exemple");
        demande.setDestinataireDemande("Destinataire Exemple");
        demande.setLieu("Lieu Exemple");
        demande.setDateDebut(new Date());
        demande.setDureePermission(5);
        demande.setDateFin(new Date());
        demande.setObjet("Objet Exemple");
        demande.setMotif("Motif Exemple");
        demande.setDestination("Destination Exemple");
        demande.setCreatedAt(LocalDateTime.now());
        return Optional.of(demande);
    }

}
