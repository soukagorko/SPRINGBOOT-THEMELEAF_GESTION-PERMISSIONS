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

    @GetMapping("/services/delete")
    public String supprimer(Long id, String keyword, int page){
        serviceRepository.deleteById(id);
//        return "redirect:/index?page="+page+"&keyword="+keyword;
        return "redirect:/services/index";
    }
    //
    @GetMapping("/services/create")
    public String ajouter(Model model){
        model.addAttribute("service", new Service());
        return "/services/create";
    }

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

}
