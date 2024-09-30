package kaba.koto.springboot.controller;

import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.auth.service.UserService;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Permission;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.repositories.PermissionRepository;
import kaba.koto.springboot.repositories.PersonnelRepository;
import kaba.koto.springboot.service.DemandeService;
import kaba.koto.springboot.service.Interface.DemandeServiceInterface;
import kaba.koto.springboot.service.PermissionService;
import kaba.koto.springboot.service.PersonnelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class PermissionController {

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

    @PostMapping("/savePermission")
    public String addPermission(@RequestParam Long demandeId, Long serviceId, Principal principal) {
        String username = principal.getName();
        permissionService.validaterDemande(demandeId, serviceId, username);
        return "redirect:/permissions";
    }

    @PostMapping("/rejeterPermission")
    public String addRejetPermission(@RequestParam Long demandeId, Long serviceId, Principal principal) {
        String username = principal.getName();
        permissionService.rejeterDemande(demandeId, serviceId, username);
        return "redirect:/permissions/du_service";
    }

    @GetMapping("/permissions/lister")
//    @GetMapping("/permissions")
    public String listePermissionAccordees(Model model) {
        model.addAttribute("permissions", permissionRepository.findAll());
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("userId", userId);
        return "permissions/list";
    }

    @GetMapping("/permissions")
    public String listPermission(Model model) {
        model.addAttribute("permissions", permissionRepository.findAll());
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("userId", userId);
        // 16 08 2024 (toutes les demandes acceptees
        List<Object[]> permissionAccordees = permissionService.getPermissionAccordeesStatistiques();
        model.addAttribute("permissionAccordeesStatistiques", permissionAccordees);
        // 16 08 2024 (toutes les demandes rejetees
        List<Object[]> permissionRejetees = permissionService.getPermissionRejeteesStatistiques();
        model.addAttribute("permissionRejeteesStatistiques", permissionRejetees);
        //
        return "permissions/index";
    }


//    @GetMapping("/permissions/accordees2")
//    public String permissionAccordees(Model model) {
//        model.addAttribute("permissions", permissionRepository.findAll());
//        //
//        List<Object[]> countForWeek = permissionService.countRecordsWithStatutOneForWeek();
//        List<Object[]> countForMonth = permissionService.countRecordsWithStatutOneForMonth();
//        List<Object[]> countForYear = permissionService.countRecordsWithStatutOneForYear();
//        List<Object[]> countOverall = permissionService.countRecordsWithStatutOneOverall();
//
//        model.addAttribute("countForWeek", countForWeek);
//        model.addAttribute("countForMonth", countForMonth);
//        model.addAttribute("countForYear", countForYear);
//        model.addAttribute("countOverall", countOverall);
//        //
//        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        User user = userRepository.findByUsername(username);
//        Long serviceId = user.getService().getId();
//        String serviceName = user.getService().getNomService();
//        long userId = user.getId();
//        model.addAttribute("serviceName", serviceName);
//        model.addAttribute("userId", userId);
//
//        model.addAttribute("demande", new Demande());
//        return "permissions/accordee2";
//    }
//
@GetMapping("/permissions/accordees")
public String listePermissionAccordees2(Model model) {
    model.addAttribute("permissions", permissionRepository.findAll());
    String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    User user = userRepository.findByUsername(username);
    Long serviceId = user.getService().getId();
    String serviceName = user.getService().getNomService();
    long userId = user.getId();
    model.addAttribute("serviceName", serviceName);
    model.addAttribute("userId", userId);
    //
    List<Object[]> countForWeek = permissionService.countRecordsWithStatutOneForWeek();
    List<Object[]> countForMonth = permissionService.countRecordsWithStatutOneForMonth();
    List<Object[]> countForYear = permissionService.countRecordsWithStatutOneForYear();
    List<Object[]> countOverall = permissionService.countRecordsWithStatutOneOverall();

    model.addAttribute("countForWeek", countForWeek);
    model.addAttribute("countForMonth", countForMonth);
    model.addAttribute("countForYear", countForYear);
    model.addAttribute("countOverall", countOverall);
    //
    //DEMANDES
    //
    List<Object[]> countDemandeForWeek = demandeService.countDemandeRecordsWithStatutOneForWeek();
    List<Object[]> countDemandeForMonth = demandeService.countDemandeRecordsWithStatutOneForMonth();
    List<Object[]> countDemandeForYear = demandeService.countDemandeRecordsWithStatutOneForYear();
    List<Object[]> countDemandeOverall = demandeService.countDemandeRecordsWithStatutOneOverall();

    model.addAttribute("countDemandeForWeek", countDemandeForWeek);
    model.addAttribute("countDemandeForMonth", countDemandeForMonth);
    model.addAttribute("countDemandeForYear", countDemandeForYear);
    model.addAttribute("countDemandeOverall", countDemandeOverall);
    //
    return "permissions/accordee";
}


    @GetMapping("/permissions/statistiques")
    public String statistiquePermissions(Model model) {
        model.addAttribute("permissions", permissionRepository.findAll());
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long serviceId = user.getService().getId();
        String serviceName = user.getService().getNomService();
        long userId = user.getId();
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("userId", userId);

        // PERMISSIONS ACCORDEES
        List<Object[]> countPermissionForWeek = permissionService.countRecordsWithStatutOneForWeek();
        List<Object[]> countPermissionForMonth = permissionService.countRecordsWithStatutOneForMonth();
        List<Object[]> countPermissionForYear = permissionService.countRecordsWithStatutOneForYear();
        List<Object[]> countOverall = permissionService.countRecordsWithStatutOneOverall();
        //
        model.addAttribute("countPermissionForWeek", countPermissionForWeek);
        model.addAttribute("countPermissionForMonth", countPermissionForMonth);
        model.addAttribute("countPermissionForYear", countPermissionForYear);
        model.addAttribute("countPermissionOverall", countOverall);
        //
        // PERMISSIONS REJETEES
        List<Object[]> countPermissionRejeteeForWeek = permissionService.countRecordsWithStatutOneForWeek();
        List<Object[]> countPermissionRejeteeForMonth = permissionService.countRecordsWithStatutOneForMonth();
        List<Object[]> countPermissionRejeteeForYear = permissionService.countRecordsWithStatutOneForYear();
        List<Object[]> countPermissionRejeteeOverall = permissionService.countRecordsWithStatutOneOverall();
        //
        model.addAttribute("countPermissionRejeteeForWeek", countPermissionRejeteeForWeek);
        model.addAttribute("countPermissionRejeteeForMonth", countPermissionRejeteeForMonth);
        model.addAttribute("countPermissionRejeteeForYear", countPermissionRejeteeForYear);
        model.addAttribute("countPermissionRejeteeOverall", countPermissionRejeteeOverall);
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
        return "permissions/statistique";
    }

//    @GetMapping("/permissions/resultats/du_service")
    @GetMapping("/permissions/du_personnel")
    public String resultatPermissionDuService(Model model, Principal principal) {
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

        //decompter et afficher toutes les permissions (accordees et rejetees)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            username = authentication.getPrincipal().toString();
        }

        // Décompte des permissions accordées
        Long accordees7Jours = permissionService.countPermissionsAccordeesLast7Days(serviceId);
        Long accordees30Jours = permissionService.countPermissionsAccordeesLast30Days(serviceId);
        Long accordeesTotalAnnuel = permissionService.countTotalPermissionsAccordees(serviceId);

        // Décompte des permissions rejetées
        Long rejetees7Jours = permissionService.countPermissionsRejeteesLast7Days(serviceId);
        Long rejetees30Jours = permissionService.countPermissionsRejeteesLast30Days(serviceId);
        Long rejeteesTotalAnnuel = permissionService.countTotalPermissionsRejetees(serviceId);

        // Liste des permissions
        List<Permission> permissions = permissionService.getAllPermissionsByService(serviceId);

        model.addAttribute("accordees7Jours", accordees7Jours);
        model.addAttribute("accordees30Jours", accordees30Jours);
        model.addAttribute("accordeesTotalAnnuel", accordeesTotalAnnuel);
        model.addAttribute("rejetees7Jours", rejetees7Jours);
        model.addAttribute("rejetees30Jours", rejetees30Jours);
        model.addAttribute("rejeteesTotalAnnuel", rejeteesTotalAnnuel);
        model.addAttribute("permissions", permissions);
        //
        return "permissions/duPersonnel";
    }

    @Autowired
    private UserService userService; // Service pour obtenir l'utilisateur connecté

    @GetMapping("/permissions/service")
    public String showPermissionParService(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            username = authentication.getPrincipal().toString();
        }

        User loggedInUser = userRepository.findByUsername(username);
        Long serviceId = loggedInUser.getService().getId();
        String nomService = loggedInUser.getService().getNomService();

        // Récupérer les permissions pour le service de l'utilisateur connecté
        List<Permission> permissions = permissionService.getPermissionsByService(serviceId);

        model.addAttribute("nomService", nomService);
        model.addAttribute("permissions", permissions);

        return "permissions/service";
    }
    // 18 08 2024
    @GetMapping("/permissions/duservice")
    public String showPermissions3(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            username = authentication.getPrincipal().toString();
        }

        User loggedInUser = userRepository.findByUsername(username);
        Long serviceId = loggedInUser.getService().getId();
        String nomService = loggedInUser.getService().getNomService();

        // Décompte des permissions accordées
        Long accordees7Jours = permissionService.countPermissionsAccordeesLast7Days(serviceId);
        Long accordees30Jours = permissionService.countPermissionsAccordeesLast30Days(serviceId);
        Long accordeesTotalAnnuel = permissionService.countTotalPermissionsAccordees(serviceId);

        // Décompte des permissions rejetées
        Long rejetees7Jours = permissionService.countPermissionsRejeteesLast7Days(serviceId);
        Long rejetees30Jours = permissionService.countPermissionsRejeteesLast30Days(serviceId);
        Long rejeteesTotalAnnuel = permissionService.countTotalPermissionsRejetees(serviceId);

        // Liste des permissions
        List<Permission> permissions = permissionService.getAllPermissionsByService(serviceId);

        // Ajouter les données au modèle
        model.addAttribute("nomService", nomService);
        model.addAttribute("accordees7Jours", accordees7Jours);
        model.addAttribute("accordees30Jours", accordees30Jours);
        model.addAttribute("accordeesTotalAnnuel", accordeesTotalAnnuel);
        model.addAttribute("rejetees7Jours", rejetees7Jours);
        model.addAttribute("rejetees30Jours", rejetees30Jours);
        model.addAttribute("rejeteesTotalAnnuel", rejeteesTotalAnnuel);
        model.addAttribute("permissions", permissions);

        return "permissions/duservice";
    }

}
