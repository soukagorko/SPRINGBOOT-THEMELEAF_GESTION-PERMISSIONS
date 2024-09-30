package kaba.koto.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DemandePermissionDTO {
    private String matricule;
    private String grade;
    private String prenom;
    private String nom;
    private Long totalDuree;
    private String nomService;
    private String sigleService;

//    public DemandePermissionDTO(String prenom, String nom, Long totalDuree, String nomService, String sigleService) {
//        this.prenom = prenom;
//        this.nom = nom;
//        this.totalDuree = totalDuree;
//        this.nomService = nomService;
//        this.sigleService = sigleService;
//    }


    public DemandePermissionDTO(String matricule, String grade, String prenom, String nom, Long totalDuree, String nomService, String sigleService) {
        this.matricule = matricule;
        this.grade = grade;
        this.prenom = prenom;
        this.nom = nom;
        this.totalDuree = totalDuree;
        this.nomService = nomService;
        this.sigleService = sigleService;
    }
}
