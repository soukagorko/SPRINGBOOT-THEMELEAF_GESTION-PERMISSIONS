package kaba.koto.springboot.auth.controller;

import jakarta.validation.Valid;
import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ServiceController {

    private ServiceRepository serviceRepository;

    @GetMapping(path = "/services")
    public String lister(Model model) {//declarer un model pour utiliser le rendu cote serveur:SPRING MVC
        List<Service> services = serviceRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listServices", services); // stocker ces données ds un model avec la creation d'unattribut listPatients
        return "/services/index";
    }
    //

//    @GetMapping(path = "/listeServices")
//    public String patients(Model model,
//                           @RequestParam(name="page", defaultValue = "0") int page,
//                           @RequestParam(name="size", defaultValue = "5") int size,
//                           @RequestParam(name="keyword", defaultValue = "") String keyword
//    ) {
//        //Page<Patient> pagePatients = serviceRepository.findAll(PageRequest.of(page, size));
//        Page<Service> pageServices = serviceRepository.findByNomContains(keyword, PageRequest.of(page, size));
//        model.addAttribute("listServices",pageServices.getContent()); // stocker ces données ds un model avec la creation d'unattribut listPatients
//        model.addAttribute("pages", new int[pageServices.getTotalPages()]); // stocker le nbre de pages
//        model.addAttribute("currentPage", page); // stocker la page courente, la page cliquée
//        model.addAttribute("keyword", keyword);
//        return "listeService";
//    }

    // Methode delete
    @GetMapping("/services/delete")
    public String supprimer(Long id, String keyword, int page){
        serviceRepository.deleteById(id);
//        return "redirect:/index?page="+page+"&keyword="+keyword;
        return "redirect:/services/index";
    }
    //
//    @GetMapping("services")
//    @ResponseBody
//    public List<Service> listServicess(){
//        return serviceRepository.findAll();
//    }

    //
    @GetMapping("/services/create")
    public String ajouter(Model model){
        model.addAttribute("service", new Service());
        return "/services/create";
    }

    //
//    @PostMapping(path="/service-save")
//    public String save(Model model, @Valid Service service, BindingResult bindingResult,
//                       @RequestParam(defaultValue = "0") int page,
//                       @RequestParam(defaultValue = "") String keyword){
//        if(bindingResult.hasErrors())
//            return "service-add";
//        serviceRepository.save(service);
////        return "redirect:/index?page="+page+"&keyword="+keyword;
//        return "redirect:/services/index";
        //
    @PostMapping(path="/services/save")
    public String save(Model model, @Valid Service service, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/services/create";
        serviceRepository.save(service);
        return "redirect:/services";
    }

    //
    @GetMapping("/services/edit")
    public String editer(Model model, Long id){// declarer un model et un id
        Service service = serviceRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(service==null) throw new RuntimeException("SERVICE INTROUVABLE !");
        model.addAttribute("service", service);
        return "/services/edit";
    }

    @GetMapping("/services/detail")
    public String show(Model model, Long id){// declarer un model et un id
        Service service = serviceRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(service==null) throw new RuntimeException("SERVICE INTROUVABLE !");
        model.addAttribute("service", service);
        return "/services/detail";
    }
//    @GetMapping("/service-edit")
//    public String editPatient(Model model, Long id, String keyword, int page){// declarer un model, un id, le mot clé et le nbre de page
//        Patient patient=serviceRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
//        if(patient==null) throw new RuntimeException("Patient introuvable !");
//        model.addAttribute("patient",patient);
//        model.addAttribute("page",page);
//        model.addAttribute("keyword",keyword);
//        return "editPatient";
//    }

}
