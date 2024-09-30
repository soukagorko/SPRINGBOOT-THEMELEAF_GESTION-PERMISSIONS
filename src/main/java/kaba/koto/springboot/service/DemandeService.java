package kaba.koto.springboot.service;

import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.auth.service.UserService;
import kaba.koto.springboot.dto.DemandePermissionDTO;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Personnel;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.repositories.PersonnelRepository;
import kaba.koto.springboot.service.Interface.DemandeSummary;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

import java.time.format.TextStyle;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private PersonnelRepository personnelRepository;
    @Autowired
    private UserRepository userRepository;

    public Demande demandePermission(Demande demande) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(demande.getDateDebut());
        calendar.add(Calendar.HOUR_OF_DAY, demande.getDureePermission());
        demande.setDateFin(calendar.getTime());
        return demandeRepository.save(demande);
    }

    // toutes les demandes par service
    public List<Demande> findAllDemande() {
        return demandeRepository.findAll();
    }
    // demande du service de l'utilisateur connecte
    public List<Demande> findByServiceId(Long serviceId) {
        return demandeRepository.findByServiceId(serviceId);
    }
    // decompter les demande du service de l'utilisateur connecte
    public long countByServiceId(Long serviceId) {
        return demandeRepository.findByServiceId(serviceId).size();
    }

    // demandes en attente
    public List<Demande> findAllDemandeEnAttente() {
        return demandeRepository.findAllByStatutIs1();
    }
    // decompter toutes les demandes
    public long countAllDemandes() {
        return demandeRepository.count();
    }
    // decompter toutes les demandes en attente
    public long countAllDemandeWithStatutZero() {
        return demandeRepository.countDemandeEnAttente();
    }
    // decompter toutes les demandes acceptees
    public long countAllDemandeWithStatutOne() {
        return demandeRepository.countDemandeAcceptees();
    }
    // decompter toutes les demandes rejetees
    public long countAllDemandeWithStatutTwo() {
        return demandeRepository.countAllDemandeRejetees();
    }


    // decompter les demande par service (par service id)
    public long countDemandesByServiceId(Long serviceId) {
        return demandeRepository.findByServiceId(serviceId).size();
    }

    // decompter toutes les demandes en attente
    public long countAllDemandeEnAttente() {
        return demandeRepository.countDemandesByStatutZero();
    }

    // decompter les demandes en attente par service
    public long countAllDemandeEnAttenteParService(int statut, Long serviceId) {
        return demandeRepository.findByStatutAndServiceId(statut, serviceId).size();
    }

    public long countDemandesByStatutAndService(int statut, Long serviceId) {
        return demandeRepository.findByStatutAndServiceId(statut, serviceId).size();
    }
    public long countDemandesByPersonnelId(Long personnelId) {
        return personnelRepository.findByServiceId(personnelId).size();
    }
    //
    public List<Demande> getAllDemandesByStatut1() {
        return demandeRepository.findAllByStatutIs1();
    }
    public List<Demande> getAllDemandeRejetees() {
        return demandeRepository.findAllDemandeRejetees();
    }

    public long countDemandesByStatut(int statut) {
        return demandeRepository.countByStatut(statut);
    }

    public List<Demande> getDemandesByStatutAndService(int statut, Long serviceId) {
        return demandeRepository.findByStatutAndServiceId(statut, serviceId);
    }

    public Map<kaba.koto.springboot.auth.entities.Service, Map<Personnel, List<Demande>>> groupDemandesByServiceAndPersonnel(Integer statut) {
        List<Demande> demandes = demandeRepository.findByStatut(statut);
        Map<kaba.koto.springboot.auth.entities.Service, Map<Personnel, List<Demande>>> result = new HashMap<>();
        for (Demande demande : demandes) {
            kaba.koto.springboot.auth.entities.Service service = demande.getService();
            Personnel personnel = demande.getPersonnel();
            result.computeIfAbsent(service, k -> new HashMap<>())
                    .computeIfAbsent(personnel, k -> new ArrayList<>())
                    .add(demande);
        }
        return result;
    }

    public Map<kaba.koto.springboot.auth.entities.Service, Long> countDemandesByService(Integer statut) {
        List<Demande> demandes = demandeRepository.findByStatut(statut);
        Map<kaba.koto.springboot.auth.entities.Service, Long> result = new HashMap<>();
        for (Demande demande : demandes) {
            kaba.koto.springboot.auth.entities.Service service = demande.getService();
            result.put(service, result.getOrDefault(service, 0L) + 1);
        }
        return result;
    }

    public long countDemandesByStatutZero() {
        return demandeRepository.countDemandesByStatutZero();
    }

    public List<Demande> findDemandesByStatut(int statut) {
        return demandeRepository.findByStatut(statut);
    }

    public long countRecordsWithStatutZero() {
        return demandeRepository.countDemandeEnAttente();
    }
//
    // ###############################################################
    // EN ATTENTES
    public List<Object[]> countDemandeRecordsWithStatutZeroForWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);
        return demandeRepository.countDemandeEnAttenteByStatutForWeek(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59));
    }
    public List<Object[]> countDemandeRecordsWithStatutZeroForMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return demandeRepository.countDemandeEnAttenteByStatutForMonth(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59));
    }
    public List<Object[]> countDemandeRecordsWithStatutZeroForYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return demandeRepository.countDemandeAccepteeByStatutForYear(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59));
    }
    public List<Object[]> countDemandeRecordsWithStatutZeroOverall() {
        return demandeRepository.countTotalDemandeEnAttenteByStatut();
    }

    // REJETEES
    public List<Object[]> countDemandeRecordsWithStatutTwoForWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);
        return demandeRepository.countDemandeRejeteeByStatutForWeek(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59));
    }

    public List<Object[]> countDemandeRecordsWithStatutTwoForMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return demandeRepository.countDemandeRejeteeByStatutForMonth(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59));
    }

    public List<Object[]> countDemandeRecordsWithStatutTwoForYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return demandeRepository.countDemandeRejeteeByStatutForYear(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59));
    }

    public List<Object[]> countDemandeRecordsWithStatutTwoOverall() {
        return demandeRepository.countTotalDemandeRejeteeByStatut();
    }

    //
    // ACCEPTEES
    public List<Object[]> countDemandeRecordsWithStatutOneForWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);
        return demandeRepository.countDemandeAccepteeByStatutForWeek(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59));
    }

    public List<Object[]> countDemandeRecordsWithStatutOneForMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return demandeRepository.countDemandeAccepteeByStatutForMonth(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59));
    }

    public List<Object[]> countDemandeRecordsWithStatutOneForYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return demandeRepository.countDemandeAccepteeByStatutForYear(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59));
    }

    public List<Object[]> countDemandeRecordsWithStatutOneOverall() {
        return demandeRepository.countTotalDemandeAccepteeByStatut();
    }

    public long countDemandesByWeek() {
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return demandeRepository.findByStatutAndDateRange(startDate.atStartOfDay(), endDate.atStartOfDay()).size();
    }

    @Autowired
    private ServiceRepository serviceRepository;

    public Map<String, Map<String, Long>> getDemandesCountByServiceAndPeriod() {
        Map<String, Map<String, Long>> result = new HashMap<>();

        List<kaba.koto.springboot.auth.entities.Service> services = serviceRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (kaba.koto.springboot.auth.entities.Service service : services) {
            Map<String, Long> counts = new HashMap<>();

            // Weekly count
            LocalDateTime weekStart = now.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).with(LocalTime.MIN);
            LocalDateTime weekEnd = now.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).with(LocalTime.MAX);
            counts.put("weekly", getCountByDateRange(service, weekStart, weekEnd));

            // Monthly count
            LocalDateTime monthStart = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
            LocalDateTime monthEnd = now.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
            counts.put("monthly", getCountByDateRange(service, monthStart, monthEnd));

            // Yearly count
            LocalDateTime yearStart = now.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
            LocalDateTime yearEnd = now.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
            counts.put("yearly", getCountByDateRange(service, yearStart, yearEnd));

            result.put(service.getNomService(), counts);
        }

        return result;
    }

    private Long getCountByDateRange(kaba.koto.springboot.auth.entities.Service service, LocalDateTime startDate, LocalDateTime endDate) {
        List<Demande> demandes = demandeRepository.findDemandesByStatutAndDateRange(startDate, endDate);
        return demandes.stream().filter(d -> d.getService().equals(service)).count();
    }

    // 13 08 2024
    //STATISTIQUE ANNUELLE 1
    public long countDemandesThisWeek() {
        LocalDateTime startOfWeek = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(LocalTime.MAX);
        return demandeRepository.countByDateDemandeBetween(startOfWeek, endOfWeek);
    }

    public long countDemandesLast30Days() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return demandeRepository.countByDateDemandeAfter(thirtyDaysAgo);
    }

    public long countDemandesThisYear() {
        LocalDateTime startOfYear = LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()).toLocalDate().atStartOfDay();
        LocalDateTime endOfYear = LocalDateTime.now().with(TemporalAdjusters.lastDayOfYear()).toLocalDate().atTime(LocalTime.MAX);
        return demandeRepository.countByDateDemandeBetween(startOfYear, endOfYear);
    }

    //STATISTIQUE ANNUELLE 2
    public long getCountForYear(int year) {
        return demandeRepository.countByStatutAndYear(year);
    }

    public long getCountForWeek(int week, int year) {
        return demandeRepository.countByStatutAndWeek(week, year);
    }

    public String getCurrentWeekName() {
        Locale locale = Locale.getDefault();
        return LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
    }

    public long getCountForLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        return demandeRepository.countByStatutAndLast30Days(startDate);
    }

    public int getCurrentWeekNumber() {
        return LocalDateTime.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }

    public int getCurrentYear() {
        return LocalDateTime.now().getYear();
    }

    // 14 08 2024
    public List<Object[]> getDemandesByYear(int year) {
        return demandeRepository.countDemandesByYear(year);
    }
    public List<Object[]> getDemandesByLast30Days() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        return demandeRepository.countDemandesByPeriod(startDate, endDate);
    }

    public List<Object[]> getDemandesByWeek(int weekNumber, int year) {
        return demandeRepository.countDemandesByWeek(weekNumber, year);
    }
    // 15 08 2024 (demandes en attentes de tous les services)
    public List<Object[]> getDemandesStatistiques() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();
        return demandeRepository.countDemandesEnAttenteByPeriods(last7Days, last30Days, currentYear);
    }

    public List<Object[]> getDemandesEnAttenteStatistiques() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();
        return demandeRepository.countDemandesEnAttenteByPeriods(last7Days, last30Days, currentYear);
    }

    // 16 08 2024 (demandes acceptees de tous les services)
    public List<Object[]> getDemandesAccepteesStatistiques() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();
        return demandeRepository.countDemandesAccepteesByPeriods(last7Days, last30Days, currentYear);
    }
    // 16 08 2024 (demandes rejetees de tous les services)
    public List<Object[]> getDemandesRejeteesStatistiques() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();
        return demandeRepository.countDemandesRejeteesByPeriods(last7Days, last30Days, currentYear);
    }

    //15 08 2024
    public List<Object[]> getDemandesStatistique() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();

        return demandeRepository.countDemandesByStatutAndService(last7Days, last30Days, currentYear);
    }

    //15 08 2024
    public List<Object[]> getDemandesStatsForCurrentUser() {
        User currentUser = getCurrentUser();
        Long serviceId = currentUser.getService().getId();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last7Days = now.minusDays(7);
        LocalDateTime last30Days = now.minusDays(30);
        int currentYear = now.getYear();

        return demandeRepository.countDemandesByService(last7Days, last30Days, currentYear, serviceId);
    }

    public String getCurrentUserServiceName() {
        User currentUser = getCurrentUser();
        return currentUser.getService().getNomService();
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }

    @Autowired
    private UserService userService;

    public long countDemandes(int statut, Long serviceId, int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return demandeRepository.countByStatutAndServiceAndDateAfter(statut, serviceId, startDate);
    }

    public long countDemandesAnnuel(int statut, Long serviceId) {
        int currentYear = LocalDate.now().getYear();
        return demandeRepository.countByStatutAndServiceAndYear(statut, serviceId, currentYear);
    }

    // 19 08 2024: cumul heures de permissions 2
    public List<Object[]> getSumDureePermissionByPersonnelForStatut1() {
        return demandeRepository.findSumDureePermissionByPersonnelForStatut1();
    }
    public List<Demande> getDemandesByStatut(int statut) {
        return demandeRepository.findByStatut(statut);
    }

     //21 08 2024: cumul heure de permissions par personnel
     @Autowired
     public DemandeService(DemandeRepository demandeRepository) {
         this.demandeRepository = demandeRepository;
     }

    // //21 08 2024: total heure de permission par personnel
    public List<DemandeSummary> getDemandesWithTotalDureeGroupedByService() {
        return demandeRepository.findTotalDureeByPersonnelGroupedByService();
    }

    public List<DemandePermissionDTO> getDemandesSummaryForCurrentUser() {
        // Récupérer l'utilisateur connecté via SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
//

        // Rechercher l'entité User personnalisée en utilisant le nom d'utilisateur
        User user = userRepository.findByUsername(username);

        // Utiliser le serviceId de l'utilisateur pour récupérer les demandes
        return demandeRepository.findDemandesSummaryByServiceId(user.getService().getId());
    }

    @Autowired
    private ResourceLoader resourceLoader;

//    public byte[] generateReport(Long demandeId) throws Exception {
//        // Récupérer la demande
//        Demande demande = demandeRepository.findById(demandeId).orElseThrow(() -> new RuntimeException("Demande not found"));
//
//        // Charger le modèle Jasper (fichier .jrxml compilé en .jasper)
//        Resource resource = resourceLoader.getResource("classpath:reports/demande_report.jasper");
//        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource.getFile());
//
//        // Préparer les données du rapport
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("nomService", demande.getService().getNomService());
//        parameters.put("prenomNomPersonnel", demande.getPersonnel().getPrenom() + " " + demande.getPersonnel().getNom());
//
//        List<Demande> demandes = Collections.singletonList(demande);
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(demandes);
//
//        // Générer le rapport
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//        return JasperExportManager.exportReportToPdf(jasperPrint);
//    }

    // IMPRIMER

    @Autowired
    private DataSource dataSource;

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public byte[] generateReport(Long demandeId) throws Exception {
        // Charger le fichier .jasper
        ClassPathResource resource = new ClassPathResource("reports/demande_report.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource.getInputStream());

        // Paramètres à passer au rapport
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("demandeId", demandeId);

        // Remplir le rapport avec des données
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        // Exporter le rapport en PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }






}
