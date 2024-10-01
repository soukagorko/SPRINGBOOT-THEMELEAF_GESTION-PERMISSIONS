package kaba.koto.springboot.controller;

import jakarta.validation.Valid;
import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.RoleRepository;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.auth.service.UserService;
import kaba.koto.springboot.auth.service.customer.CustomUserDetail;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Personnel;
import kaba.koto.springboot.repositories.PersonnelRepository;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import kaba.koto.springboot.service.PersonnelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PersonnelController {
//
    private PersonnelRepository personnelRepository;
    private ServiceRepository serviceRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    //
    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private UserService userService;

//    @GetMapping(path = "admin/personnels")
    @GetMapping(path = "admin/personnels")
    public String lister(Model model) {//declarer un model pour utiliser le rendu cote serveur:SPRING MVC
        List<Personnel> personnels = personnelRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listPersonnels", personnels); // stocker ces données ds un model avec la creation d'unattribut listPatients
        return "/personnels/index";
    }
    //
    @GetMapping(path = "listPersonnels")
    public String lister2(Model model) {//declarer un model pour utiliser le rendu cote serveur:SPRING MVC
        List<Personnel> personnels = personnelRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listPersonnels", personnels); //

        long totalPersonnels = personnelService.countAllPersonnels();
        model.addAttribute("totalPersonnels", totalPersonnels);
        return "/personnels/list";
    }
    //
    @GetMapping("/personnels")
    public String listByService(Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);

        Long serviceId = user.getService().getId();
        List<Personnel> personnels = personnelService.findByServiceId(serviceId);
        long personnelCount = personnelService.countByServiceId(serviceId);
        String serviceName = user.getService().getNomService();
        String sigleService = user.getService().getSigleService();
        long userId = user.getId();

        model.addAttribute("personnels", personnels);
        model.addAttribute("personnelCount", personnelCount);
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("sigleService", sigleService);
        model.addAttribute("userId", userId);
//        return "personnels/effectif";
        return "personnels/personnel";
    }
    //
    @GetMapping("/personnels/all")
    public String getAllDataPersonnels(Model model) {
        List<Service> services = serviceRepository.findAll();
        Map<String, List<Personnel>> personnelsByService = personnelService.findAllPersonnel()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getService().getNomService()));
        model.addAttribute("services", services);
//        model.addAttribute("personnelByService", personnelByService);

        long totalPersonnels = personnelService.countAllPersonnels();
        Map<String, Long> personnelCountByService = services.stream()
                .collect(Collectors.toMap(
                        Service::getNomService,
                        service -> personnelService.countPersonnelsByServiceId(service.getId())
                ));

        model.addAttribute("personnelsByService", personnelsByService);
        model.addAttribute("totalPersonnels", totalPersonnels);
        model.addAttribute("personnelCountByService", personnelCountByService);

        return "personnels/all";
    }

    @GetMapping("/personnels/create")
    public String createForm(Model model){
        model.addAttribute("personnel", new Personnel());
        List<Service> services = serviceRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listServices", services);
        return "personnels/create";
    }
    //
    @PostMapping(path="/personnels/save")
    public String save(Model model, @Valid Personnel personnel, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "personnels/create";
        personnelRepository.save(personnel);
        return "redirect:/personnels";
    }

    @GetMapping("/personnels/edit")
    public String editPersonnel(Model model, Long id){// declarer un model, un id, le mot clé et le nbre de page
        Personnel personnel =personnelRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(personnel==null) throw new RuntimeException("PERSONNEL INTROUVABLE !");
        model.addAttribute("personnel",personnel);
        return "personnels/edit";
    }

    //
    @GetMapping("/personnels/detail")
    public String detailPersonnel(Model model, Long id){// declarer un model et un id
        Personnel personnel =personnelRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(personnel==null) throw new RuntimeException("PERSONNEL INTROUVABLE !");
        model.addAttribute("personnel",personnel);
        return "/personnels/detail";
    }

    // Methode delete
    @GetMapping("/personnels/delete")
    public String delete(Long id){
        personnelRepository.deleteById(id);
        return "redirect:/personnels/index";
    }

    @GetMapping("/personnels/du_service")
    public String getEmployesByService(Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);

        Long serviceId = user.getService().getId();
        List<Personnel> personnels = personnelService.findByServiceId(serviceId);
        long personnelCount = personnelService.countByServiceId(serviceId);
        String serviceName = user.getService().getNomService();
        String sigleService = user.getService().getSigleService();
        long userId = user.getId();

        model.addAttribute("personnels", personnels);
        model.addAttribute("personnelCount", personnelCount);
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("sigleService", sigleService);
        model.addAttribute("userId", userId);
        return "personnels/du_service";
    }

    @GetMapping("/personnel/service/{serviceId}")
    public String getPersonnelByService(@PathVariable Long serviceId, Model model) {
        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + serviceId));
        List<Personnel> personnelList = personnelService.getPersonnelByService(service);
        model.addAttribute("personnelList", personnelList);
        return "personnels/personnelList";
    }

}
