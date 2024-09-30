package kaba.koto.springboot.controller;

import kaba.koto.springboot.entities.Departement;
import kaba.koto.springboot.entities.Employe;
import kaba.koto.springboot.repositories.EmployeeRepository;
import kaba.koto.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeService;

    private EmployeeRepository employeeRepository;

    @GetMapping("/employes")
    public String home(Model model) {
        List<Departement> departements = employeService.findAllDepartements();
        model.addAttribute("departements", departements);
        model.addAttribute("employe", new Employe());
        return "employes/index";
    }

    @PostMapping("/saveEmploye")
    public String saveEmploye(@ModelAttribute Employe employe, @RequestParam Long departementId) {
        Departement departement = employeService.findAllDepartements().stream()
                .filter(dep -> dep.getId().equals(departementId))
                .findFirst()
                .orElse(null);
        employe.setDepartement(departement);
        employe.setCreationDate(LocalDateTime.now());
        employeService.saveEmploye(employe);
        return "redirect:/employes";
    }

    @GetMapping("/count")
    public String countRecords(Model model) {
        List<Object[]> countForWeek = employeService.countRecordsWithStatutOneForWeek();
        List<Object[]> countForMonth = employeService.countRecordsWithStatutOneForMonth();
        List<Object[]> countForYear = employeService.countRecordsWithStatutOneForYear();
        List<Object[]> countOverall = employeService.countRecordsWithStatutOneOverall();

        model.addAttribute("countForWeek", countForWeek);
        model.addAttribute("countForMonth", countForMonth);
        model.addAttribute("countForYear", countForYear);
        model.addAttribute("countOverall", countOverall);

        return "employes/count";
    }

//    @GetMapping("/list")
////    public String listEmployesByDepartement(@RequestParam String departementId, Model model) {
////        List<Employe> employes = employeService.listEmployesByDepartement(departementId);
//
////        public String listEmployesByDepartement(@RequestParam String departementNom, Model model) {
////        List<Employe> employes = employeService.listEmployesByDepartement(departementNom);
//        model.addAttribute("employes", employes);
//        return "employes/list";
//    }

@GetMapping("/lister")
    public String listEmployesByDepartement(Model model) {
    model.addAttribute("employes", employeeRepository.findAll());
    return "employes/list";
}
}
