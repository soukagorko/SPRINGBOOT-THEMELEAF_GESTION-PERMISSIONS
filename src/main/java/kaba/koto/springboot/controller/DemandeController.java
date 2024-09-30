package kaba.koto.springboot.controller;

import jakarta.servlet.http.HttpServletResponse;
import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.auth.service.ServiceService;
import kaba.koto.springboot.auth.service.customer.CustomUserDetail;
import kaba.koto.springboot.dto.DemandeDTO;
import kaba.koto.springboot.dto.DemandePrintDTO;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Personnel;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.repositories.PersonnelRepository;
import kaba.koto.springboot.service.DemandeService;
import kaba.koto.springboot.service.Interface.DemandeServiceInterface;
import kaba.koto.springboot.service.PersonnelService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "*")
@Controller
@AllArgsConstructor
public class DemandeController {
//    //
    private DemandeRepository demandeRepository;
    private DemandeService demandeService;
    private DemandeServiceInterface demandeServiceInterface;
    //
    private UserRepository userRepository;
    private PersonnelRepository personnelRepository;
    private PersonnelService personnelService;
    private ServiceRepository serviceRepository;
    //
    @GetMapping("/personnel/create_demande")
    public String faireUneDemande(Model model, Long id){// declarer un model, un id, le mot clé et le nbre de page
        Personnel personnel =personnelRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(personnel==null) throw new RuntimeException("PERSONNEL INTROUVABLE !");
        model.addAttribute("personnel",personnel);
        model.addAttribute("demande", new Demande());

        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);

        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("userId", userId);

        return "/personnels/createDemande";
    }
//
    @PostMapping(path="/demandes/save")
    public String saveDemande(Demande demande, Model model) {
        Demande dataDemande = demandeService.demandePermission(demande);
        model.addAttribute("dataDemande", dataDemande);
        return "redirect:/demandes/duPersonnelEnAttente";
    }
    //
    @GetMapping("/demandes")
    public String allData(Model model) {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        String roleName = user.getRole().getNomRole();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("nomRole", roleName);
        model.addAttribute("userId", userId);
        // toutes les demandes en attente
        List<Object[]> demandesEnAttente = demandeService.getDemandesEnAttenteStatistiques();
        model.addAttribute("demandesEnAttenteStatistiques", demandesEnAttente);
        // toutes les demandes acceptees
        List<Object[]> demandesAcceptees = demandeService.getDemandesAccepteesStatistiques();
        model.addAttribute("demandesAccepteesStatistiques", demandesAcceptees);
        // toutes les demandes rejetees
        List<Object[]> demandesRejetees = demandeService.getDemandesRejeteesStatistiques();
        model.addAttribute("demandesRejeteesStatistiques", demandesRejetees);
        //
        return "demandes/index";
    }

    @GetMapping("/demandes/all")
    public String allDataDemande(Model model) {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        String roleName = user.getRole().getNomRole();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("nomRole", roleName);
        model.addAttribute("userId", userId);
        //
        String service = user.getService().getNomService();
        model.addAttribute("service", service);
        //
        model.addAttribute("serviceId", serviceId);
        String sigleService = user.getService().getSigleService();
        model.addAttribute("sigleService", sigleService);
        //
        List<Demande> demandeEnAttente = demandeService.getAllDemandesByStatut1();
        // Group by service and count
        Map<Service, Long> demandesParService = demandeEnAttente.stream()
                .collect(Collectors.groupingBy(Demande::getService, Collectors.counting()));
        model.addAttribute("nbreDemandeEnAttente", demandeEnAttente.size());
        //
        return "demandes/all";
    }

    //
    @GetMapping("/demandes/en-attentes/all")
    public String demandeAllEnAttente(Model model) {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        long userId = user.getId();
        model.addAttribute("userId", userId);
        //
        List<Demande> demandeEnAttente = demandeService.getAllDemandesByStatut1();
        // Group by service and count
        Map<Service, Long> demandesParService = demandeEnAttente.stream()
                .collect(Collectors.groupingBy(Demande::getService, Collectors.counting()));
        model.addAttribute("nbreDemandeEnAttente", demandeEnAttente.size());
        //
        return "demandes/en-attente";
    }


    @GetMapping("/demandes/en_attentes/du_personnel")
    public String demandeDuPersonnelEnAttente(Model model, Principal principal) {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        long userId = user.getId();
        model.addAttribute("userId", userId);
        String role = user.getRole().getNomRole();
        model.addAttribute("role", role);
        String service = user.getService().getNomService();
        model.addAttribute("service", service);
        Long serviceId = user.getService().getId();
        model.addAttribute("serviceId", serviceId);
        String sigleService = user.getService().getSigleService();
        model.addAttribute("sigleService", sigleService);
        //
        List<Demande> demandeEnAttente = demandeService.getAllDemandesByStatut1();
        // Group by service and count
        Map<Service, Long> demandesParService = demandeEnAttente.stream()
                .collect(Collectors.groupingBy(Demande::getService, Collectors.counting()));
        model.addAttribute("nbreDemandeEnAttente", demandeEnAttente.size());

            long demandesEnAttente7Jours = demandeService.countDemandes(0, serviceId, 7);
            long demandesEnAttente30Jours = demandeService.countDemandes(0, serviceId, 30);
            long demandesEnAttenteAnnuel = demandeService.countDemandesAnnuel(0, serviceId);

            long demandesAcceptees7Jours = demandeService.countDemandes(1, serviceId, 7);
            long demandesAcceptees30Jours = demandeService.countDemandes(1, serviceId, 30);
            long demandesAccepteesAnnuel = demandeService.countDemandesAnnuel(1, serviceId);

            long demandesRejetees7Jours = demandeService.countDemandes(2, serviceId, 7);
            long demandesRejetees30Jours = demandeService.countDemandes(2, serviceId, 30);
            long demandesRejeteesAnnuel = demandeService.countDemandesAnnuel(2, serviceId);

            model.addAttribute("demandesEnAttente7Jours", demandesEnAttente7Jours);
            model.addAttribute("demandesEnAttente30Jours", demandesEnAttente30Jours);
            model.addAttribute("demandesEnAttenteAnnuel", demandesEnAttenteAnnuel);
            model.addAttribute("demandesAcceptees7Jours", demandesAcceptees7Jours);
            model.addAttribute("demandesAcceptees30Jours", demandesAcceptees30Jours);
            model.addAttribute("demandesAccepteesAnnuel", demandesAccepteesAnnuel);
            model.addAttribute("demandesRejetees7Jours", demandesRejetees7Jours);
            model.addAttribute("demandesRejetees30Jours", demandesRejetees30Jours);
            model.addAttribute("demandesRejeteesAnnuel", demandesRejeteesAnnuel);
            //
           return "demandes/duPersonnelEnAttente";
    }

    @GetMapping("/demandes/du_service/en_attentes")
    public String demandeDuServiceEnAttente(Model model) {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        long userId = user.getId();
        model.addAttribute("userId", userId);
        String role = user.getRole().getNomRole();
        model.addAttribute("role", role);
        String service = user.getService().getNomService();
        model.addAttribute("service", service);
        Long serviceId = user.getService().getId();
        model.addAttribute("serviceId", serviceId);
        String sigleService = user.getService().getSigleService();
        model.addAttribute("sigleService", sigleService);
        //
        List<Demande> demandeEnAttente = demandeService.getAllDemandesByStatut1();
        // Group by service and count
        Map<Service, Long> demandesParService = demandeEnAttente.stream()
                .collect(Collectors.groupingBy(Demande::getService, Collectors.counting()));
        model.addAttribute("nbreDemandeEnAttente", demandeEnAttente.size());
        //
        return "demandes/duServiceEnAttente";
    }

    @GetMapping("/demandes/resultats")
    public String resultatDemande(Model model) {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        String roleName = user.getRole().getNomRole();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("nomRole", roleName);
        model.addAttribute("userId", userId);

        String service = user.getService().getNomService();
        model.addAttribute("service", service);
        model.addAttribute("serviceId", serviceId);
        String sigleService = user.getService().getSigleService();
        model.addAttribute("sigleService", sigleService);
        // STATS ALL DEMANDES
        // toutes les demandes en attente
        List<Object[]> demandesEnAttente = demandeService.getDemandesEnAttenteStatistiques();
        model.addAttribute("demandesEnAttenteStatistiques", demandesEnAttente);
        // toutes les demandes acceptees
        List<Object[]> demandesAcceptees = demandeService.getDemandesAccepteesStatistiques();
        model.addAttribute("demandesAccepteesStatistiques", demandesAcceptees);
        // toutes les demandes rejetees
        List<Object[]> demandesRejetees = demandeService.getDemandesRejeteesStatistiques();
        model.addAttribute("demandesRejeteesStatistiques", demandesRejetees);
        //
        return "demandes/resultat";
    }

    @GetMapping("/demandes/resultats/du_personnel")
    public String resultatDemandeDuPersonnel(Model model) {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        long userId = user.getId();
        model.addAttribute("userId", userId);
        String role = user.getRole().getNomRole();
        model.addAttribute("role", role);
        String service = user.getService().getNomService();
        model.addAttribute("service", service);
        Long serviceId = user.getService().getId();
        model.addAttribute("serviceId", serviceId);
        String sigleService = user.getService().getSigleService();
        model.addAttribute("sigleService", sigleService);
        //
        return "demandes/resultatDemandePersonnel";
    }

    @GetMapping("/demandes/par_service")
    public String getAllDataDemande(Model model) {
        List<Service> services = serviceRepository.findAll();
        Map<String, List<Demande>> demandesByService = demandeService.findAllDemandeEnAttente()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getService().getNomService()));
        model.addAttribute("services", services);
        long totalDemandes = demandeService.countAllDemandes();
        Map<String, Long> demandeCountByService = services.stream()
                .collect(Collectors.toMap(
                        Service::getNomService,
                        service -> demandeService.countDemandesByServiceId(service.getId())
                ));
        model.addAttribute("demandesByService", demandesByService);
        model.addAttribute("totalDemandes", totalDemandes);
        model.addAttribute("demandeCountByService", demandeCountByService);
        return "demandes/par_service";
    }

    @GetMapping("/demandes/du_service")
    public String getDemandesByService(Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        List<Demande> demandes = demandeService.findByServiceId(serviceId);
        long demandeCount = demandeService.countByServiceId(serviceId);
        String serviceName = user.getService().getNomService();
        String sigleService = user.getService().getSigleService();
        long userId = user.getId();
        model.addAttribute("demandes", demandes);
        model.addAttribute("demandeCount", demandeCount);
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("sigleService", sigleService);
        model.addAttribute("userId", userId);
        return "demandes/du_service";
    }



    @Autowired
    private ServiceService serviceService;

    @GetMapping("/demandes/rejetees/all")
    public String demandeRejeteeAll(Model model) {
        model.addAttribute("demandes", demandeRepository.findAllDemandeRejetees());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        long userId = user.getId();
        model.addAttribute("userId", userId);
        //
        List<Demande> demandeRejetees = demandeService.getAllDemandeRejetees();
        // Group by service and count
        Map<Service, Long> demandesRejeteeParService = demandeRejetees.stream()
                .collect(Collectors.groupingBy(Demande::getService, Collectors.counting()));
        model.addAttribute("nbreDemandeRejetees", demandeRejetees.size());
        //
        return "demandes/rejetee";
    }

    @GetMapping("/demandes/rejetees/du_personnel")
    public String demandeDuServiceRejete(Model model) {
        model.addAttribute("demandes", demandeRepository.findAllDemandeRejetees());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        long userId = user.getId();
        model.addAttribute("userId", userId);
        String role = user.getRole().getNomRole();
        model.addAttribute("role", role);
        String service = user.getService().getNomService();
        model.addAttribute("service", service);
        Long serviceId = user.getService().getId();
        model.addAttribute("serviceId", serviceId);
        String sigleService = user.getService().getSigleService();
        model.addAttribute("sigleService", sigleService);
        //
        List<Demande> demandeDuServicRejetees = demandeService.getAllDemandeRejetees();
        // Group by service and count
        Map<Service, Long> demandesParService = demandeDuServicRejetees.stream()
                .collect(Collectors.groupingBy(Demande::getService, Collectors.counting()));
        model.addAttribute("nbreDemandeDuServicRejetees", demandeDuServicRejetees.size());
        //
        return "demandes/rejeteeDuPersonnel";
    }

    @GetMapping("/demandes/statistiques")
    public String statistiqueDemandes(Model model,
                @RequestParam(value = "year", required = false) Integer year,
                @RequestParam(value = "week", required = false) Integer week)
        {
        model.addAttribute("demandes", demandeRepository.findAll());
        //
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        String roleName = user.getRole().getNomRole();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("nomRole", roleName);
        model.addAttribute("userId", userId);
        //
        //DEMANDES
        //En attentes
        List<Object[]> countDemandeEnAttenteForWeek = demandeService.countDemandeRecordsWithStatutOneForWeek();
        List<Object[]> countDemandecountDemandeEnAttenteForMonth = demandeService.countDemandeRecordsWithStatutOneForMonth();
        List<Object[]> countDemandecountDemandeEnAttenteForYear = demandeService.countDemandeRecordsWithStatutOneForYear();
        List<Object[]> countTotalDemandeEnAttente = demandeService.countDemandeRecordsWithStatutOneOverall();

        model.addAttribute("countDemandeEnAttenteForWeek", countDemandeEnAttenteForWeek);
        model.addAttribute("countDemandecountDemandeEnAttenteForMonth", countDemandecountDemandeEnAttenteForMonth);
        model.addAttribute("countDemandecountDemandeEnAttenteForYear", countDemandecountDemandeEnAttenteForYear);
        model.addAttribute("countTotalDemandeEnAttente", countTotalDemandeEnAttente);
        //
        //En acceptees
        List<Object[]> countDemandeAccepteeForWeek = demandeService.countDemandeRecordsWithStatutOneForWeek();
        List<Object[]> countDemandecountDemandeAccepteeForMonth = demandeService.countDemandeRecordsWithStatutOneForMonth();
        List<Object[]> countDemandecountDemandeAccepteeForYear = demandeService.countDemandeRecordsWithStatutOneForYear();
        List<Object[]> countTotalDemandeAcceptee = demandeService.countDemandeRecordsWithStatutOneOverall();

        model.addAttribute("countDemandeAccepteeForWeek", countDemandeAccepteeForWeek);
        model.addAttribute("countDemandecountDemandeAccepteeForMonth", countDemandecountDemandeAccepteeForMonth);
        model.addAttribute("countDemandecountDemandeAccepteeForYear", countDemandecountDemandeAccepteeForYear);
        model.addAttribute("countTotalDemandeAcceptee", countTotalDemandeAcceptee);
        //
        //En acceptees
        List<Object[]> countDemandeRejeteeForWeek = demandeService.countDemandeRecordsWithStatutTwoForWeek();
        List<Object[]> countDemandecountDemandeRejeteeForMonth = demandeService.countDemandeRecordsWithStatutTwoForMonth();
        List<Object[]> countDemandecountDemandeRejeteeForYear = demandeService.countDemandeRecordsWithStatutTwoForYear();
        List<Object[]> countTotalDemandeRejetee = demandeService.countDemandeRecordsWithStatutTwoOverall();

        model.addAttribute("countDemandeRejeteeForWeek", countDemandeRejeteeForWeek);
        model.addAttribute("countDemandecountDemandeRejeteeForMonth", countDemandecountDemandeRejeteeForMonth);
        model.addAttribute("countDemandecountDemandeRejeteeForYear", countDemandecountDemandeRejeteeForYear);
        model.addAttribute("countTotalDemandeRejetee", countTotalDemandeRejetee);
        //
        long countAllDemande = demandeService.countAllDemandes();
        model.addAttribute("nbreTotalDemandes", countAllDemande);
        //
        long countStatutZero = demandeService.countAllDemandeEnAttente();
        model.addAttribute("nbreDemandeEnAttentes", countStatutZero);
        //
        long countStatutOne = demandeService.countAllDemandeWithStatutOne();
        model.addAttribute("nbreDemandeAcceptees", countStatutOne);
        //
        long countStatutTwo = demandeService.countAllDemandeWithStatutTwo();
        model.addAttribute("nbreDemandeRejetees", countStatutTwo);
        //
        model.addAttribute("demandesThisWeek", demandeService.countDemandesThisWeek());
        model.addAttribute("demandesLast30Days", demandeService.countDemandesLast30Days());
        model.addAttribute("demandesThisYear", demandeService.countDemandesThisYear());
        //

            // Si l'année n'est pas fournie, utiliser l'année en cours
            if (year == null) {
                year = demandeService.getCurrentYear();
            }

            // Si la semaine n'est pas fournie, utiliser la semaine en cours
            if (week == null) {
                week = demandeService.getCurrentWeekNumber();
            }

            String currentWeekName = demandeService.getCurrentWeekName();

            long demandesThisYear = demandeService.getCountForYear(year);
            long demandesThisWeek = demandeService.getCountForWeek(week, year);
            long demandesLast30Days = demandeService.getCountForLast30Days();

            model.addAttribute("year", year);
            model.addAttribute("week", week);
            model.addAttribute("currentWeekName", currentWeekName);
            model.addAttribute("demandesThisYear", demandesThisYear);
            model.addAttribute("demandesThisWeek", demandesThisWeek);
            model.addAttribute("demandesLast30Days", demandesLast30Days);

            // 14 08 2024
            List<Object[]> demandesStatistiques = demandeService.getDemandesStatistiques();
            model.addAttribute("demandesStatistiques", demandesStatistiques);

            return "demandes/statistique";
    }

    @GetMapping(value = "demandes/detailPDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> imprimerDemande(@PathVariable("id") long id) throws JRException, IOException {
        Demande demandeAImprimer = demandeServiceInterface.getOneDemande(id);
        if (demandeAImprimer==null) {
            return ResponseEntity.badRequest().build();
        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(List.of(
                DemandePrintDTO.fromEntity(demandeAImprimer)
        ), false);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("grade", "Agent de Police");
        parameters.put("demande_ID", demandeAImprimer.getId());
//        JasperReport compileReport = JasperCompileManager
//                .compileReport(new FileInputStream("src/main/resources/demande.jrxml"));
        JasperReport compileReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/demande.jrxml"));
//        JasperReport compileReport = JasperCompileManager
//                .compileReport(new FileInputStream("src/main/resources/templates/demandes/detailPDF.jrxml"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
        System.err.println(data);
        HttpHeaders headers = new HttpHeaders();
//        String filename = "permission-"+demandeAImprimer.getPrenom()+"-"+demandeAImprimer.getNom()+"-"+demandeAImprimer.getDateCreation()+".pdf";
        String filename = "permission-"+demandeAImprimer.getPersonnel()+"-"+demandeAImprimer.getService()+"-"+demandeAImprimer.getDateDemande()+".pdf";
        headers.add("Content-Disposition", "inline; filename="+filename);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

}
