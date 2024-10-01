package kaba.koto.springboot.controller;

import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.dto.DemandePermissionDTO;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.repositories.PermissionRepository;
import kaba.koto.springboot.repositories.PersonnelRepository;
import kaba.koto.springboot.service.DemandeService;
import kaba.koto.springboot.service.Interface.DemandeServiceInterface;
import kaba.koto.springboot.service.Interface.DemandeSummary;
import kaba.koto.springboot.service.PermissionService;
import kaba.koto.springboot.service.PersonnelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class StatistiqueController {

    private DemandeRepository demandeRepository;
    private PermissionRepository permissionRepository;
    private UserRepository userRepository;

    private DemandeService demandeService;
    private DemandeServiceInterface demandeServiceInterface;
    //
    private PersonnelRepository personnelRepository;
    private PersonnelService personnelService;
    private ServiceRepository serviceRepository;
    //

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/statistiques")
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
        // ########### STAT DEMANDES ###############
        // toutes les demandes en attente
        List<Object[]> demandesEnAttente = demandeService.getDemandesEnAttenteStatistiques();
        model.addAttribute("demandesEnAttenteStatistiques", demandesEnAttente);
        // toutes les demandes acceptees
        List<Object[]> demandesAcceptees = demandeService.getDemandesAccepteesStatistiques();
        model.addAttribute("demandesAccepteesStatistiques", demandesAcceptees);
        // toutes les demandes rejetees
        List<Object[]> demandesRejetees = demandeService.getDemandesRejeteesStatistiques();
        model.addAttribute("demandesRejeteesStatistiques", demandesRejetees);
        // ################### STATS PERMISSIONS #############
        // toutes les demandes acceptees
        List<Object[]> permissionAccordees = permissionService.getPermissionAccordeesStatistiques();
        model.addAttribute("permissionAccordeesStatistiques", permissionAccordees);
        // toutes les demandes rejetees
        List<Object[]> permissionRejetees = permissionService.getPermissionRejeteesStatistiques();
        model.addAttribute("permissionRejeteesStatistiques", permissionRejetees);
        // ################ STATS TOTAL JOURS DE PERMISSIONS PAR PERSONNEL #############
        List<DemandeSummary> demandeSummaries = demandeService.getDemandesWithTotalDureeGroupedByService();
        // Regrouper les résultats par nom de service
        Map<String, List<DemandeSummary>> demandesGroupedByService = demandeSummaries.stream()
                .collect(Collectors.groupingBy(DemandeSummary::getNomService));
        model.addAttribute("demandesGroupedByService", demandesGroupedByService);
        //
        return "statistiques/index";
    }


    // 19 08 2024
    @GetMapping("/statistiques/permissions/total_jour_par_service")
    public String getCumulHeurePermissionParPersonnelDuService(Model model) {
        List<DemandeSummary> demandeSummaries = demandeService.getDemandesWithTotalDureeGroupedByService();
        // Regrouper les résultats par nom de service
        Map<String, List<DemandeSummary>> demandesGroupedByService = demandeSummaries.stream()
                .collect(Collectors.groupingBy(DemandeSummary::getNomService));
        model.addAttribute("demandesGroupedByService", demandesGroupedByService);
        return "statistiques/cumulHeurePermissionParPersonnelDuService";
    }

    @GetMapping("/statistiques/permissions/total_jour_du_personnel")
    public String getTotalJourDePermissionDuPersonnelDeMonService(Model model, Principal principal) {
//        model.addAttribute("demandes", demandeRepository.findAll());
//        //
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

        long demandesEnAttente7Jours = demandeService.countDemandes(0, serviceId, 7);
        long demandesEnAttente30Jours = demandeService.countDemandes(0, serviceId, 30);
        long demandesEnAttenteAnnuel = demandeService.countDemandesAnnuel(0, serviceId);

        long demandesAcceptees7Jours = demandeService.countDemandes(1, serviceId, 7);
        long demandesAcceptees30Jours = demandeService.countDemandes(1, serviceId, 30);
        long demandesAccepteesAnnuel = demandeService.countDemandesAnnuel(1, serviceId);

        long demandesRejetees7Jours = demandeService.countDemandes(2, serviceId, 7);
        long demandesRejetees30Jours = demandeService.countDemandes(2, serviceId, 30);
        long demandesRejeteesAnnuel = demandeService.countDemandesAnnuel(2, serviceId);

//            model.addAttribute("nomService", loggedInUser.getService().getNomService());
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
        //String username = principal.getName();
        List<Map<String, Object>> cumulData = permissionService.getCumulativeDurationByPersonnelInUserService(username);
        model.addAttribute("cumulData", cumulData);
        return "statistiques/cumulJourPermissionDuPersonnel";
        //
        //return "demandes/duPersonnelEnAttente";
    }

    @GetMapping("/statistiques/totale-duree")
    public String showTotalDuree(Model model) {
        // Appeler le service pour obtenir les données pour l'utilisateur connecté
        List<DemandePermissionDTO> demandeSummaries = demandeService.getDemandesSummaryForCurrentUser();

        if (!demandeSummaries.isEmpty()) {
            String nomService = demandeSummaries.get(0).getNomService();
            String sigleService = demandeSummaries.get(0).getSigleService();
            model.addAttribute("nomService", nomService);
            model.addAttribute("sigleService", sigleService);
        }

        model.addAttribute("demandeSummaries", demandeSummaries);
        return "statistiques/totale-duree";
    }
}
