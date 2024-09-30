package kaba.koto.springboot.auth.controller;

import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.ServiceRepository;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.repositories.PersonnelRepository;
import kaba.koto.springboot.service.DemandeService;
import kaba.koto.springboot.service.Interface.DemandeServiceInterface;
import kaba.koto.springboot.service.PersonnelService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    private DemandeRepository demandeRepository;
    private DemandeService demandeService;
    private DemandeServiceInterface demandeServiceInterface;
    //
    private UserRepository userRepository;
    private PersonnelRepository personnelRepository;
    private PersonnelService personnelService;
    private ServiceRepository serviceRepository;
    //
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/accueil")
    public String defaultAfterLogin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("");
        //
        if (role.equals("ROLE_ADMIN")) {
           return "/home";
        }
        else if (role.equals("ROLE_USER"))
        {
            return "redirect:/home";
        }
        else if (role.equals("ROLE_SPCSA"))
        {
            return "redirect:/home";
        }
        else if (role.equals("ROLE_ChefDeService"))
        {
            return "redirect:/home";
        }
        else if (role.equals("ROLE_ChefDeBrigade"))
        {
            return "redirect:/home";
        }
        else if (role.equals("ROLE_SECRETAIRE-BRIGADE"))
        {
            return "redirect:/home";
        }
        else
        {
            return "redirect:/";
        }
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin/home";
        //
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
