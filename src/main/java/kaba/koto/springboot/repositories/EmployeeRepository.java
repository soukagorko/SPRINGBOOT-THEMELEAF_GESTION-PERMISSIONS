package kaba.koto.springboot.repositories;

import kaba.koto.springboot.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employe, Long> {

    @Query("SELECT e.departement.nom, COUNT(e) FROM Employe e WHERE e.statut = 1 AND e.creationDate BETWEEN :startOfWeek AND :endOfWeek GROUP BY e.departement.nom")
    List<Object[]> countByStatutForWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT e.departement.nom, COUNT(e) FROM Employe e WHERE e.statut = 1 AND e.creationDate BETWEEN :startOfMonth AND :endOfMonth GROUP BY e.departement.nom")
    List<Object[]> countByStatutForMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT e.departement.nom, COUNT(e) FROM Employe e WHERE e.statut = 1 AND e.creationDate BETWEEN :startOfYear AND :endOfYear GROUP BY e.departement.nom")
    List<Object[]> countByStatutForYear(@Param("startOfYear") LocalDateTime startOfYear, @Param("endOfYear") LocalDateTime endOfYear);

    @Query("SELECT e.departement.nom, COUNT(e) FROM Employe e WHERE e.statut = 1 GROUP BY e.departement.nom")
    List<Object[]> countByStatutOverall();

//    List<Employe> findByDepartementNom(String departementNom);
    //List<Employe> findByDepartementId(String departementId);

}
