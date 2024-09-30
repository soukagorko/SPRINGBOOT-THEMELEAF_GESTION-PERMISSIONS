package kaba.koto.springboot.repositories;

import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.dto.DemandePermissionDTO;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Permission;
import kaba.koto.springboot.entities.Personnel;
import kaba.koto.springboot.service.Interface.DemandeSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {

    @Query("SELECT d FROM Demande d WHERE d.statut = 0")
    List<Demande> findAllByStatutIs1();


    // compter les demande en attente
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 0")
    long countDemandesByStatutZero();

    // compter les demandes en attente par service
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = :statut AND d.service.id = :serviceId")
    long countDemandeEnAttenteParService(@Param("statut") int statut, @Param("serviceId") Long serviceId);


    List<Demande> findByServiceId(Long serviceId);
    //
    long countByStatut(int statut);
    List<Demande> findByStatut(int statut);

    List<Demande> findByStatutAndServiceId(int statut, Long serviceId);

    List<Demande> findByPersonnelId(Long personnelId);

    // compter toutes les demandes en attentes
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 0")
    long countDemandeEnAttente();
    // compter toutes les demandes acceptees
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 1")
    long countDemandeAcceptees();
    // compter toutes les demandes rejetees
    @Query("SELECT d FROM Demande d WHERE d.statut = 2")
    List<Demande> findAllDemandeRejetees();
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 2")
    long countAllDemandeRejetees();

//    @Query("SELECT d FROM Demande d WHERE d.statut = 1 AND d.createdAt BETWEEN :startDate AND :endDate")
//    List<Demande> findDemandesByStatutAndDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT d FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startDate AND :endDate")
    List<Demande> findDemandesByStatutAndDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

// 07 08 2024
    @Query("SELECT d FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startDate AND :endDate")
    List<Demande> findByStatutAndDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // ####################### EN ATTENTE : STATUT = 0 ###################################
    // Demande en attente statut=0
    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 0 AND d.dateDemande BETWEEN :startOfWeek AND :endOfWeek GROUP BY d.service.nomService")
    List<Object[]> countDemandeEnAttenteByStatutForWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);
    
    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 0 AND d.dateDemande BETWEEN :startOfMonth AND :endOfMonth GROUP BY d.service.nomService")
    List<Object[]> countDemandeEnAttenteByStatutForMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 0 AND d.dateDemande BETWEEN :startOfYear AND :endOfYear GROUP BY d.service.nomService")
    List<Object[]> countDemandeEnAttenteByStatutForYear(@Param("startOfYear") LocalDateTime startOfYear, @Param("endOfYear") LocalDateTime endOfYear);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 0 GROUP BY d.service.nomService")
    List<Object[]> countTotalDemandeEnAttenteByStatut();

    // ####################### ACCEPTEES : STATUT = 1 ###################################
    // Demande accordee statut=1
    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startOfWeek AND :endOfWeek GROUP BY d.service.nomService")
    List<Object[]> countDemandeByStatutForWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startOfWeek AND :endOfWeek GROUP BY d.service.nomService")
    List<Object[]> countDemandeAccepteeByStatutForWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startOfMonth AND :endOfMonth GROUP BY d.service.nomService")
    List<Object[]> countDemandeAccepteeByStatutForMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startOfYear AND :endOfYear GROUP BY d.service.nomService")
    List<Object[]> countDemandeAccepteeByStatutForYear(@Param("startOfYear") LocalDateTime startOfYear, @Param("endOfYear") LocalDateTime endOfYear);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 GROUP BY d.service.nomService")
    List<Object[]> countTotalDemandeAccepteeByStatut();
    // Demande rejete statut=2
    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 2 AND d.dateDemande BETWEEN :startOfWeek AND :endOfWeek GROUP BY d.service.nomService")
    List<Object[]> countDemandeRejeteesByStatutForWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 2 AND d.dateDemande BETWEEN :startOfWeek AND :endOfWeek GROUP BY d.service.nomService")
    List<Object[]> countDemandeRejeteeByStatutForWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 2 AND d.dateDemande BETWEEN :startOfMonth AND :endOfMonth GROUP BY d.service.nomService")
    List<Object[]> countDemandeRejeteeByStatutForMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 2 AND d.dateDemande BETWEEN :startOfYear AND :endOfYear GROUP BY d.service.nomService")
    List<Object[]> countDemandeRejeteeByStatutForYear(@Param("startOfYear") LocalDateTime startOfYear, @Param("endOfYear") LocalDateTime endOfYear);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 2 GROUP BY d.service.nomService")
    List<Object[]> countTotalDemandeRejeteeByStatut();

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startOfMonth AND :endOfMonth GROUP BY d.service.nomService")
    List<Object[]> countDemandeByStatutForMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 AND d.dateDemande BETWEEN :startOfYear AND :endOfYear GROUP BY d.service.nomService")
    List<Object[]> countDemandeByStatutForYear(@Param("startOfYear") LocalDateTime startOfYear, @Param("endOfYear") LocalDateTime endOfYear);

    @Query("SELECT d.service.nomService, COUNT(d) FROM Demande d WHERE d.statut = 1 GROUP BY d.service.nomService")
    List<Object[]> countDemandeByStatutOverall();

// Fin 13 08 2024

    // STATISTIQUE ANNUELLE 1
    long countByDateDemandeBetween(LocalDateTime startDate, LocalDateTime endDate);
    long countByDateDemandeAfter(LocalDateTime date);

    // STATISTIQUE ANNUELLE 2
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 0 AND d.dateDemande >= :startDate")
    long countByStatutAndLast30Days(@Param("startDate") LocalDateTime startDate);
    //
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 0 AND FUNCTION('YEAR', d.dateDemande) = :year")
    long countByStatutAndYear(@Param("year") int year);

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 0 AND FUNCTION('WEEK', d.dateDemande) = :week AND FUNCTION('YEAR', d.dateDemande) = :year")
    long countByStatutAndWeek(@Param("week") int week, @Param("year") int year);

    // 14 08 2024
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 0 THEN 1 ELSE 0 END), COUNT(d) " +
            "FROM Demande d JOIN d.service s " +
            "WHERE YEAR(d.dateDemande) = :year " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesByYear(@Param("year") int year);

    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 0 THEN 1 ELSE 0 END), COUNT(d) " +
            "FROM Demande d JOIN d.service s " +
            "WHERE d.dateDemande >= :startDate AND d.dateDemande <= :endDate " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesByPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 0 THEN 1 ELSE 0 END), COUNT(d) " +
            "FROM Demande d JOIN d.service s " +
            "WHERE WEEK(d.dateDemande) = :weekNumber AND YEAR(d.dateDemande) = :year " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesByWeek(@Param("weekNumber") int weekNumber, @Param("year") int year);

    //15 08 2024 (demandes en attentes de tous les services)
//    @Query("SELECT s.nomService, " +
//            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END) AS last7Days, " +
//            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END) AS last30Days, " +
//            "SUM(CASE WHEN d.statut = 0 AND YEAR(d.dateDemande) = :currentYear THEN 1 ELSE 0 END) AS currentYearTotal " +
//            "FROM Demande d JOIN d.service s " +
//            "GROUP BY s.nomService")
//    List<Object[]> countDemandesByPeriods(@Param("last7Days") LocalDateTime last7Days,
//                                          @Param("last30Days") LocalDateTime last30Days,
//                                          @Param("currentYear") int currentYear);
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END) AS last7Days, " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END) AS last30Days, " +
            "SUM(CASE WHEN d.statut = 0 AND YEAR(d.dateDemande) = :currentYear THEN 1 ELSE 0 END) AS currentYearTotal " +
            "FROM Demande d JOIN d.service s " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesEnAttenteByPeriods(@Param("last7Days") LocalDateTime last7Days,
                                          @Param("last30Days") LocalDateTime last30Days,
                                          @Param("currentYear") int currentYear);

    //15 08 2024 (demandes en acceptees de tous les services)
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 1 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END) AS last7Days, " +
            "SUM(CASE WHEN d.statut = 1 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END) AS last30Days, " +
            "SUM(CASE WHEN d.statut = 1 AND YEAR(d.dateDemande) = :currentYear THEN 1 ELSE 0 END) AS currentYearTotal " +
            "FROM Demande d JOIN d.service s " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesAccepteesByPeriods(@Param("last7Days") LocalDateTime last7Days,
                                                   @Param("last30Days") LocalDateTime last30Days,
                                                   @Param("currentYear") int currentYear);

    //15 08 2024 (demandes en rejetees de tous les services)
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 2 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END) AS last7Days, " +
            "SUM(CASE WHEN d.statut = 2 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END) AS last30Days, " +
            "SUM(CASE WHEN d.statut = 2 AND YEAR(d.dateDemande) = :currentYear THEN 1 ELSE 0 END) AS currentYearTotal " +
            "FROM Demande d JOIN d.service s " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesRejeteesByPeriods(@Param("last7Days") LocalDateTime last7Days,
                                                   @Param("last30Days") LocalDateTime last30Days,
                                                   @Param("currentYear") int currentYear);

    // 15 08 2024
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 0 AND YEAR(d.dateDemande) = :year THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 1 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 1 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 1 AND YEAR(d.dateDemande) = :year THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 2 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 2 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 2 AND YEAR(d.dateDemande) = :year THEN 1 ELSE 0 END) " +
            "FROM Demande d JOIN d.service s " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesByStatutAndService(@Param("last7Days") LocalDateTime last7Days,
                                                   @Param("last30Days") LocalDateTime last30Days,
                                                   @Param("year") int year);

    //
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 0 AND YEAR(d.dateDemande) = :currentYear THEN 1 ELSE 0 END) " +
            "FROM Demande d " +
            "JOIN d.service s " +
            "JOIN d.user u " +
            "WHERE u.id = :userId " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesByServiceForUser1(@Param("last7Days") LocalDateTime last7Days,
                                                  @Param("last30Days") LocalDateTime last30Days,
                                                  @Param("currentYear") int currentYear,
                                                  @Param("userId") Long userId);
    //

    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last7Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 0 AND d.dateDemande >= :last30Days THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.statut = 0 AND YEAR(d.dateDemande) = :currentYear THEN 1 ELSE 0 END) " +
            "FROM Demande d " +
            "JOIN d.service s " +
            "WHERE s.id = :serviceId " +
            "GROUP BY s.nomService")
    List<Object[]> countDemandesByService(@Param("last7Days") LocalDateTime last7Days,
                                          @Param("last30Days") LocalDateTime last30Days,
                                          @Param("currentYear") int currentYear,
                                          @Param("serviceId") Long serviceId);

    //
//    @Query("SELECT COUNT(d) FROM Demande d WHERE d.service.id = :serviceId AND d.statut = :statut AND d.dateDemande BETWEEN :startDate AND :endDate")
//    long countDemandesByServiceAndStatutAndDateRange(@Param("serviceId") Long serviceId, @Param("statut") int statut, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
//
//    @Query("SELECT COUNT(d) FROM Demande d WHERE d.service.id = :serviceId AND d.statut = :statut AND YEAR(d.dateDemande) = :year")
//    long countAnnualDemandesByServiceAndStatut(@Param("serviceId") Long serviceId, @Param("statut") int statut, @Param("year") int year);

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = :statut AND d.service.id = :serviceId AND d.dateDemande >= :startDate")
    long countByStatutAndServiceAndDateAfter(@Param("statut") int statut, @Param("serviceId") Long serviceId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = :statut AND d.service.id = :serviceId AND YEAR(d.dateDemande) = :year")
    long countByStatutAndServiceAndYear(@Param("statut") int statut, @Param("serviceId") Long serviceId, @Param("year") int year);

    // 19 08 2024: cumul heures de permissions 2
    @Query("SELECT d.personnel.id, SUM(d.dureePermission) FROM Demande d WHERE d.statut = 1 GROUP BY d.personnel.id")
    List<Object[]> findSumDureePermissionByPersonnelForStatut1();
    //List<Demande> findByStatut(int statut);

    // 21 08 2024: cumul heures de permissions de chaque personne
//    @Query("SELECT d.personnel.id AS personnelId, d.personnel.prenom AS prenom, d.personnel.nom AS nom, SUM(d.dureePermission) AS totalDuree " +
//            "FROM Demande d " +
//            "WHERE d.statut = 1 " +
//            "GROUP BY d.personnel.id, d.personnel.prenom, d.personnel.nom " +
//            "ORDER BY SUM(d.dureePermission) DESC")
//    List<DemandeSummary> findTotalDureeByPersonnel();

    // 21 08 2024: total heure de permission par personnel
    @Query("SELECT s.nomService AS nomService, s.sigleService AS sigleService, p.id AS personnelId, p.matricule AS matricule, p.grade AS grade, p.prenom AS prenom, p.nom AS nom, SUM(d.dureePermission) AS totalDuree " +
            "FROM Demande d " +
            "JOIN d.personnel p " +
            "JOIN p.service s " +
            "WHERE d.statut = 1 " +
            "GROUP BY s.nomService, p.id, p.prenom, p.nom " +
            "ORDER BY s.nomService, SUM(d.dureePermission) DESC")
    List<DemandeSummary> findTotalDureeByPersonnelGroupedByService();

    //21 08 2024: total heure de permission par personnel du meme service
    @Query("SELECT new kaba.koto.springboot.dto.DemandePermissionDTO(p.matricule, p.grade, p.prenom, p.nom, SUM(d.dureePermission), s.nomService, s.sigleService) " +
            "FROM Demande d " +
            "JOIN d.personnel p " +
            "JOIN p.service s " +
            "WHERE d.statut = 1 AND s.id = :serviceId " +
            "GROUP BY p.id, p.prenom, p.nom, s.nomService, s.sigleService " +
            "ORDER BY SUM(d.dureePermission) DESC")
    List<DemandePermissionDTO> findDemandesSummaryByServiceId(@Param("serviceId") Long serviceId);

}
