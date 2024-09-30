package kaba.koto.springboot.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandeDTO {
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
    private LocalDateTime createdAt;
}
