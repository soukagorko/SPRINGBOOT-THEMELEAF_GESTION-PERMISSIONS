package kaba.koto.springboot.service;

import kaba.koto.springboot.entities.Departement;
import kaba.koto.springboot.entities.Employe;
import kaba.koto.springboot.repositories.DepartmentRepository;
import kaba.koto.springboot.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeRepository;

    @Autowired
    private DepartmentRepository departementRepository;

    public void saveEmploye(Employe employe) {
        employeRepository.save(employe);
    }

    public List<Object[]> countRecordsWithStatutOneForWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);
        return employeRepository.countByStatutForWeek(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59));
    }

    public List<Object[]> countRecordsWithStatutOneForMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return employeRepository.countByStatutForMonth(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59));
    }

    public List<Object[]> countRecordsWithStatutOneForYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return employeRepository.countByStatutForYear(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59));
    }

    public List<Object[]> countRecordsWithStatutOneOverall() {
        return employeRepository.countByStatutOverall();
    }

//    public List<Employe> listEmployesByDepartement(String departementNom) {
//        return employeRepository.findByDepartementNom(departementNom);
//    }

//    public List<Employe> listEmployesByDepartement(String nom) {
//        return employeRepository.findByDepartementNom(nom);
//    }

    public List<Departement> findAllDepartements() {
        return departementRepository.findAll();
    }
}
