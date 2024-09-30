package kaba.koto.springboot.auth.controller;

import jakarta.validation.Valid;
import kaba.koto.springboot.auth.entities.Role;
import kaba.koto.springboot.auth.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class RoleController {

    private RoleRepository roleRepository;

    @GetMapping(path = "roles")
    public String lister(Model model) {//declarer un model pour utiliser le rendu cote serveur:SPRING MVC
        List<Role> roles = roleRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listRoles", roles); // stocker ces données ds un model avec la creation d'unattribut listPatients
        return "/roles/index";
    }
    @GetMapping("roles/delete")
    public String supprimer(Long id, String keyword, int page){
        roleRepository.deleteById(id);
        return "redirect:/roles/index";
    }
    //
    @GetMapping("/roles/create")
    public String ajouter(Model model){
        model.addAttribute("role", new Role());
        return "/roles/create";
    }
    //
    @PostMapping(path="/roles/save")
    public String save(Model model, @Valid Role role, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/roles/create";
        roleRepository.save(role);
        return "redirect:/roles";
    }

    //
    @GetMapping("/roles/edit")
    public String editer(Model model, Long id){// declarer un model et un id
        Role role = roleRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(role==null) throw new RuntimeException("ROLE INTROUVABLE !");
        model.addAttribute("role", role);
        return "/roles/edit";
    }

    @GetMapping("/roles/detail")
    public String show(Model model, Long id){// declarer un model et un id
        Role role = roleRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(role==null) throw new RuntimeException("ROLE INTROUVABLE !");
        model.addAttribute("role", role);
        return "/roles/detail";
    }

}
