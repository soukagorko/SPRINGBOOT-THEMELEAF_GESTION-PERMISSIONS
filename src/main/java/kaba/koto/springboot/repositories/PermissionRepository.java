package kaba.koto.springboot.repositories;

import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByServiceId(Long serviceId);
//    List<Permission> findByService(String service);

    @Query("SELECT p.service.nomService, COUNT(p) FROM Permission p WHERE p.statut = 1 AND p.datePermission BETWEEN :startOfWeek AND :endOfWeek GROUP BY p.service.nomService")
    List<Object[]> countByStatutForWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT p.service.nomService, COUNT(p) FROM Permission p WHERE p.statut = 1 AND p.datePermission BETWEEN :startOfMonth AND :endOfMonth GROUP BY p.service.nomService")
    List<Object[]> countByStatutForMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT p.service.nomService, COUNT(p) FROM Permission p WHERE p.statut = 1 AND p.datePermission BETWEEN :startOfYear AND :endOfYear GROUP BY p.service.nomService")
    List<Object[]> countByStatutForYear(@Param("startOfYear") LocalDateTime startOfYear, @Param("endOfYear") LocalDateTime endOfYear);

    @Query("SELECT p.service.nomService, COUNT(p) FROM Permission p WHERE p.statut = 1 GROUP BY p.service.nomService")
    List<Object[]> countByStatutOverall();

    @Query("SELECT COUNT(p) FROM Permission p WHERE p.statut = 1")
    long countByStatut();

    @Query("SELECT COUNT(p) FROM Permission p WHERE p.statut = 0")
    long countDemandeEnAttente();

    @Query("SELECT COUNT(p) FROM Permission p WHERE p.statut = 2")
    long countDemandeRejetee();
//
    @Query("SELECT COUNT(p) FROM Permission p WHERE p.statut = 1")
    long countByStatutForOne();

        @Query("SELECT COUNT(p) FROM Permission p WHERE p.statut = 2 AND p.datePermission BETWEEN :startOfYear AND :endOfYear")
    long countByStatutForTwo(@Param("startOfYear") LocalDateTime startOfYear, @Param("endOfYear") LocalDateTime endOfYear);

//    @Query("SELECT p FROM Permission p WHERE p.statut = 1 AND p.datePermission BETWEEN :startDate AND :endDate")
//    List<Permission> findByStatutAndDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Permission p WHERE p.statut = 1 AND p.datePermission BETWEEN :startDate AND :endDate")
    List<Permission> findByStatutAndDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //
    //17 08 2024 (permissions en accordees de tous les services)
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN p.statut = 1 AND p.datePermission >= :last7Days THEN 1 ELSE 0 END) AS last7Days, " +
            "SUM(CASE WHEN p.statut = 1 AND p.datePermission >= :last30Days THEN 1 ELSE 0 END) AS last30Days, " +
            "SUM(CASE WHEN p.statut = 1 AND YEAR(p.datePermission) = :currentYear THEN 1 ELSE 0 END) AS currentYearTotal " +
            "FROM Permission p JOIN p.service s " +
            "GROUP BY s.nomService")
    List<Object[]> countPermissionAccordeesByPeriods(@Param("last7Days") LocalDateTime last7Days,
                                                   @Param("last30Days") LocalDateTime last30Days,
                                                   @Param("currentYear") int currentYear);

    //15 08 2024 (demandes en rejetees de tous les services)
    @Query("SELECT s.nomService, " +
            "SUM(CASE WHEN p.statut = 2 AND p.datePermission >= :last7Days THEN 1 ELSE 0 END) AS last7Days, " +
            "SUM(CASE WHEN p.statut = 2 AND p.datePermission >= :last30Days THEN 1 ELSE 0 END) AS last30Days, " +
            "SUM(CASE WHEN p.statut = 2 AND YEAR(p.datePermission) = :currentYear THEN 1 ELSE 0 END) AS currentYearTotal " +
            "FROM Permission p JOIN p.service s " +
            "GROUP BY s.nomService")
    List<Object[]> countPermissionRejeteesByPeriods(@Param("last7Days") LocalDateTime last7Days,
                                                  @Param("last30Days") LocalDateTime last30Days,
                                                  @Param("currentYear") int currentYear);

    // 18 08 2024
//    @Query("SELECT * FROM Permission)
//    List<Permission> findAllPermission();

    // 18 08 2024
    @Query("SELECT COUNT(p) FROM Permission p WHERE p.service.id = :serviceId AND p.statut = :statut AND p.datePermission >= :startDate")
    long countByServiceAndStatutAndDateAfter(@Param("serviceId") Long serviceId, @Param("statut") int statut, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(p) FROM Permission p WHERE p.service.id = :serviceId AND p.statut = :statut")
    long countByServiceAndStatut(@Param("serviceId") Long serviceId, @Param("statut") int statut);

    //
    long countByServiceId(Long serviceId);
    //
//    @Query("SELECT p FROM Permission p WHERE p.service.id = :serviceId")
//    List<Permission> findAllByServiceId(@Param("serviceId") Long serviceId);
    //
    @Query("SELECT COUNT(p) FROM Permission p WHERE p.service.id = :serviceId AND p.statut = 1 AND p.datePermission >= :startDate")
    Long countPermissionsAccordees(@Param("serviceId") Long serviceId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(p) FROM Permission p WHERE p.service.id = :serviceId AND p.statut = 2 AND p.datePermission >= :startDate")
    Long countPermissionsRejetees(@Param("serviceId") Long serviceId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(p) FROM Permission p WHERE p.service.id = :serviceId AND p.statut = 1")
    Long countTotalPermissionsAccordees(@Param("serviceId") Long serviceId);

    @Query("SELECT COUNT(p) FROM Permission p WHERE p.service.id = :serviceId AND p.statut = 2")
    Long countTotalPermissionsRejetees(@Param("serviceId") Long serviceId);

    @Query("SELECT p FROM Permission p WHERE p.service.id = :serviceId")
    List<Permission> findAllByServiceId(@Param("serviceId") Long serviceId);

    // 19 08 2024: le cumul des heures d'absences 1
    @Query("SELECT d.personnel.id, SUM(d.dureePermission) FROM Demande d WHERE d.statut = 1 GROUP BY d.personnel.id")
    List<Object[]> findSumDureePermissionByPersonnelWithStatut1();

//    // 19 08 2024: cumul heures de permissions 2
//    @Query("SELECT d.personnel.id, SUM(d.dureePermission) FROM Demande d WHERE d.statut = 1 GROUP BY d.personnel.id")
//    List<Object[]> findSumDureePermissionByPersonnelForStatut1();
//
//    List<Demande> findByStatut(int statut);

    // 19 08 2024: cumul heures de permissions 3
    @Query("SELECT d.personnel, SUM(d.dureePermission) FROM Demande d WHERE d.statut = 1 GROUP BY d.personnel")
    List<Object[]> findSumDureePermissionByPersonnelForStatut1_3();

    // 19 08 2024: cumul heures de permissions 4
    @Query("SELECT d.personnel, SUM(d.dureePermission) as totalDuration FROM Demande d WHERE d.statut = 1 GROUP BY d.personnel ORDER BY totalDuration DESC")
    List<Object[]> findSumDureePermissionByPersonnelForStatut1Ordered();

    // 19 08 2024: cumul heures de permissions par service
    @Query("SELECT p.service.nomService AS nomService, p.service.sigleService AS sigleService, p.matricule AS matricule, p.grade AS grade, p.prenom AS prenom, p.nom AS nom, SUM(d.dureePermission) AS totalDuree " +
            "FROM Demande d " +
            "JOIN d.personnel p " +
            "WHERE d.statut = 1 " +
            "GROUP BY p.id, p.service.nomService " +
            "ORDER BY p.service.nomService ASC, SUM(d.dureePermission) DESC")
    List<Map<String, Object>> findCumulativeDurationByPersonnelAndService();

    // 19 08 2024: cumul heures de permissions par service
    @Query("SELECT d.personnel, SUM(d.dureePermission) as totalDuration FROM Demande d WHERE d.statut = 1 AND d.personnel.service.id = :serviceId GROUP BY d.personnel ORDER BY totalDuration DESC")
    List<Object[]> findSumDureePermissionByPersonnelForStatut1Ordered(@Param("serviceId") Long serviceId);

    //19 08 2024: cumul heures de permissions du service de l'utilisateur connecte
    @Query("SELECT  p.service.nomService AS nomService, p.matricule AS matricule, p.grade AS grade, p.prenom AS prenom, p.nom AS nom, SUM(d.dureePermission) AS totalDuree " +
            "FROM Demande d " +
            "JOIN d.personnel p " +
            "JOIN p.service s " +
            "JOIN User u ON u.service.id = s.id " +
            "WHERE d.statut = 1 AND u.username = :username " +
            "GROUP BY p.id " +
            "ORDER BY SUM(d.dureePermission) DESC")
    List<Map<String, Object>> findCumulativeDurationByPersonnelInUserService(@Param("username") String username);

    // 19 08 2024: cumul heures de permissions par service et par personnel
    @Query("SELECT p.service.nomService AS nomService, p.service.sigleService AS sigleService, p.matricule AS matricule, p.grade AS grade, p.prenom AS prenom, p.nom AS nom, SUM(d.dureePermission) AS totalDuree " +
            "FROM Demande d " +
            "JOIN d.personnel p " +
            "WHERE d.statut = 1 " +
//            "GROUP BY p.id" +
//            "ORDER BY p.service.nomService ASC, SUM(d.dureePermission) DESC")
            "GROUP BY p.id")
    List<Map<String, Object>> findCumulDureePermissionByPersonnelAndService();



}
