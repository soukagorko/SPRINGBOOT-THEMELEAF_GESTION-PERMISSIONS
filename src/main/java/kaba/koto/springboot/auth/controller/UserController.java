package kaba.koto.springboot.auth.controller;

import jakarta.validation.Valid;
import kaba.koto.springboot.auth.dto.UserDTO;
import kaba.koto.springboot.auth.entities.Role;
import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.auth.repositories.RoleRepository;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import kaba.koto.springboot.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private RoleRepository roleRepository;

    @GetMapping(path = "users")
    public String lister(Model model) {//declarer un model pour utiliser le rendu cote serveur:SPRING MVC
        List<User> users = userRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listUsers", users); // stocker ces données ds un model avec la creation d'unattribut listPatients
        return "/users/index";
    }

    @GetMapping("/users/delete")
    public String supprimer(Long id, String keyword, int page){
        userRepository.deleteById(id);
        return "redirect:/users";
    }
    //
    @GetMapping("/users/create")
    public String ajouter(Model model){
        model.addAttribute("user", new User());
        //
        List<Service> services = serviceRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listServices", services);
        //
        List<Role> roles = roleRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listRoles", roles);
        //
        return "/users/create";
    }
    //
    @PostMapping(path="/users/save")
    public String save(Model model, @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/users/create";
        userRepository.save(user);
        return "redirect:/users";
    }

    //
    @GetMapping("/users/edit")
    public String editer(Model model, Long id){// declarer un model et un id
        User user = userRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(user==null) throw new RuntimeException("UTILISATEUR INTROUVABLE !");
        model.addAttribute("user", user);
        List<Service> services = serviceRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listServices", services);
        //
        List<Role> roles = roleRepository.findAll();//recuperer la liste des patients ds la BD
        model.addAttribute("listRoles", roles);
        return "/users/edit";
    }

    @GetMapping("/users/detail")
    public String show(Model model, Long id){// declarer un model et un id
        User user = userRepository.findById(id).orElse(null);// rechercher un patient par son id, on returne null s'il nexiste pas
        if(user==null) throw new RuntimeException("ROLE INTROUVABLE !");
        model.addAttribute("user", user);
        return "/users/detail";
    }

}
