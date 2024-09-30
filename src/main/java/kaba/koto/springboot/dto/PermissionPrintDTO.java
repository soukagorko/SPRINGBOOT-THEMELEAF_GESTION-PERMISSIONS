package kaba.koto.springboot.dto;

import kaba.koto.springboot.entities.Demande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PermissionPrintDTO {

//    private Long idDemande;
    private Long id;

    private String user;
    private String personnel;
    private String service;

    private String destinataireDemande;

    private String lieu;

    private Date dateDebut;

    private int dureePermission;

    private Date dateFin;

    private String objet;
    private String motif;

    private String destination;

    //    private Date createdAt;
    private LocalDateTime createdAt;

    public static PermissionPrintDTO fromEntity(Demande demande) {

        return PermissionPrintDTO.builder()
//                .idDemande(demande.getIdDemande())
                .id(demande.getId())
                .user(demande.getUser().toString())
                .personnel(demande.getPersonnel().toString())
                .service(demande.getService().toString())
                .destinataireDemande(demande.getDestinataireDemande())
                .lieu(demande.getLieu())
                .dureePermission(demande.getDureePermission())
                .dateDebut(demande.getDateDebut())
                .dateFin(demande.getDateFin())
                .motif(demande.getMotif())
                .destination(demande.getDestination())
                .objet(demande.getObjet())
                .createdAt(demande.getCreatedAt())
                .build();

    }
}
