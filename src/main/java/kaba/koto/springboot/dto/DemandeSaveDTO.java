package kaba.koto.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeSaveDTO {

    private int userId;

    private int serviceId;

    private int personnelId;

    private String destinataireDemande;

    private String lieu;

    private Date dateDebut;

    private int dureePermission;

    private Date dateFin;

    private String objet;

    private String motif;

    private String destination;

//    private Date created_at;
}
