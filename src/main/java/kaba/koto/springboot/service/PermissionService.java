package kaba.koto.springboot.service;

import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.auth.service.UserService;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Permission;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void validaterDemande(Long demandeId, Long serviceId, String username) {
        // Modifier le statut de la demande
        Demande demande = demandeRepository.findById(demandeId).orElseThrow();
        serviceId = demande.getService().getId();
        demande.setStatut(1);
        demandeRepository.save(demande);
        // Récupérer l'utilisateur connecté
        User user = userRepository.findByUsername(username);
        // Enregistrer l'action
        Permission permission = new Permission();
        permission.setDatePermission(new Date());
        permission.setStatut(1);
        permission.setUser(user);
        permission.setDemande(demande);
        permission.setService(demande.getService());
        permissionRepository.save(permission);
    }

    @Transactional
    public void rejeterDemande(Long demandeId, Long serviceId, String username) {
        // Modifier le statut de la demande
        Demande demande = demandeRepository.findById(demandeId).orElseThrow();
        serviceId = demande.getService().getId();
        demande.setStatut(2);
        demandeRepository.save(demande);
        // Récupérer l'utilisateur connecté
        User user = userRepository.findByUsername(username);
        // Enregistrer l'action
        Permission permission = new Permission();
        permission.setDatePermission(new Date());
        permission.setStatut(2);
        permission.setUser(user);
        permission.setDemande(demande);
        permission.setService(demande.getService());
        permissionRepository.save(permission);
    }


    public long countRecords() {
        return permissionRepository.count();
    }

    public long countRecordsWithStatutOne() {
        return permissionRepository.countByStatut();
    }

    public long countRecordsWithStatutZero() {
        return permissionRepository.countDemandeEnAttente();
    }

    public long countRecordsWithStatutTwo() {
        return permissionRepository.countDemandeRejetee();
    }

    public long countAllRecordsWithStatutOne() {
        return permissionRepository.countByStatutForOne();
    }

//
    public List<Object[]> countRecordsWithStatutOneForWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);
        return permissionRepository.countByStatutForWeek(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59));
    }

    public List<Object[]> countRecordsWithStatutOneForMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return permissionRepository.countByStatutForMonth(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59));
    }

    public List<Object[]> countRecordsWithStatutOneForYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return permissionRepository.countByStatutForYear(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59));
    }

    public List<Object[]> countRecordsWithStatutOneOverall() {
        return permissionRepository.countByStatutOverall();
    }

    public long countPermissionsByWeek() {
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        //return permissionRepository.findByStatutAndDateRange(startDate, endDate).size();
        return permissionRepository.findByStatutAndDateRange(startDate.atStartOfDay(), endDate.atStartOfDay()).size();
    }

    public long countPermissionsByMonth() {
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        //return permissionRepository.findByStatutAndDateRange(startDate, endDate).size();
        return permissionRepository.findByStatutAndDateRange(startDate.atStartOfDay(), endDate.atStartOfDay()).size();
    }

    public long countPermissionsByYear() {
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        //return permissionRepository.findByStatutAndDateRange(startDate, endDate).size();
        return permissionRepository.findByStatutAndDateRange(startDate.atStartOfDay(), endDate.atStartOfDay()).size();
    }

    //
    // 17 08 2024 (permissions accordees de tous les services)
    public List<Object[]> getPermissionAccordeesStatistiques() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();
        return permissionRepository.countPermissionAccordeesByPeriods(last7Days, last30Days, currentYear);
    }
    // 17 08 2024 (permissions rejetees de tous les services)
    public List<Object[]> getPermissionRejeteesStatistiques() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();
        return permissionRepository.countPermissionRejeteesByPeriods(last7Days, last30Days, currentYear);
    }

    // 18 08 2024
    // decompter toutes les permissions (accordees et rejetees)
    public Map<String, Long> getStatutCounts(Long serviceId, int statut) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        LocalDateTime startOfYear = now.withDayOfYear(1).with(LocalTime.MIN);

        long countLast7Days = permissionRepository.countByServiceAndStatutAndDateAfter(serviceId, statut, last7Days);
        long countLast30Days = permissionRepository.countByServiceAndStatutAndDateAfter(serviceId, statut, last30Days);
        long countYearly = permissionRepository.countByServiceAndStatutAndDateAfter(serviceId, statut, startOfYear);

        Map<String, Long> counts = new HashMap<>();
        counts.put("last7Days", countLast7Days);
        counts.put("last30Days", countLast30Days);
        counts.put("yearly", countYearly);

        return counts;
    }

    public long getTotalCount(Long serviceId, int statut) {
        return permissionRepository.countByServiceAndStatut(serviceId, statut);
    }

    //
    public long getTotalPermissionsCountByService(Long serviceId) {
        return permissionRepository.countByServiceId(serviceId);
    }
    //
    public List<Permission> getPermissionsByService(Long serviceId) {
        return permissionRepository.findAllByServiceId(serviceId);
    }
    // 18 08 2024
    public Long countPermissionsAccordeesLast7Days(Long serviceId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        return permissionRepository.countPermissionsAccordees(serviceId, sevenDaysAgo);
    }

    public Long countPermissionsAccordeesLast30Days(Long serviceId) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        return permissionRepository.countPermissionsAccordees(serviceId, thirtyDaysAgo);
    }

    public Long countTotalPermissionsAccordees(Long serviceId) {
        return permissionRepository.countTotalPermissionsAccordees(serviceId);
    }

    public Long countPermissionsRejeteesLast7Days(Long serviceId) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        return permissionRepository.countPermissionsRejetees(serviceId, sevenDaysAgo);
    }

    public Long countPermissionsRejeteesLast30Days(Long serviceId) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        return permissionRepository.countPermissionsRejetees(serviceId, thirtyDaysAgo);
    }

    public Long countTotalPermissionsRejetees(Long serviceId) {
        return permissionRepository.countTotalPermissionsRejetees(serviceId);
    }

    public List<Permission> getAllPermissionsByService(Long serviceId) {
        return permissionRepository.findAllByServiceId(serviceId);
    }

    // 19 08 2024: cumul des heures de permissions 1
    public List<Object[]> getSumDureePermissionByPersonnelWithStatut1() {
        return permissionRepository.findSumDureePermissionByPersonnelWithStatut1();
    }

//    // 19 08 2024: cumul heures de permissions 2
//    public List<Object[]> getSumDureePermissionByPersonnelForStatut1() {
//        return permissionRepository.findSumDureePermissionByPersonnelForStatut1();
//    }
//
//    public List<Demande> getDemandesByStatut(int statut) {
//        return permissionRepository.findByStatut(statut);
//    }

    // 19 08 2024: cumul heures de permissions 3
    public List<Object[]> getSumDureePermissionByPersonnelForStatut1_3() {
        return permissionRepository.findSumDureePermissionByPersonnelForStatut1_3();
    }

    // 19 08 2024: cumul heures de permissions 4
    public List<Object[]> getSumDureePermissionByPersonnelForStatut1Ordered() {
        return permissionRepository.findSumDureePermissionByPersonnelForStatut1Ordered();
    }

    //// 19 08 2024: cumul heures de permissions par service
    public List<Map<String, Object>> getCumulativeDurationByPersonnelAndService() {
        return permissionRepository.findCumulativeDurationByPersonnelAndService();
    }

//    public List<Object[]> getSumDureePermissionByPersonnelForStatut1Ordered(Long serviceId) {
//        return permissionRepository.findSumDureePermissionByPersonnelForStatut1Ordered(serviceId);
//    }

    // 19 08 2024: cumul heures de permissions du service de l'utilisateur connecte
    public List<Map<String, Object>> getCumulativeDurationByPersonnelInUserService(String username) {
        return permissionRepository.findCumulativeDurationByPersonnelInUserService(username);
    }

    //// 21 08 2024: cumul heures de permissions par service
    public List<Map<String, Object>> getCumulDureePermissionByPersonnelAndService() {
        return permissionRepository.findCumulDureePermissionByPersonnelAndService();
    }



}
