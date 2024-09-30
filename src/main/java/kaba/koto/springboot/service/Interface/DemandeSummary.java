package kaba.koto.springboot.service.Interface;

public interface DemandeSummary {
    Long getPersonnelId();
    String getMatricule();
    String getGrade();
    String getPrenom();
    String getNom();
    Long getTotalDuree();

    String getNomService();
//    Long getPersonnelId();
//    String getPrenom();
//    String getNom();
//    Long getTotalDuree(); // en heures
}
