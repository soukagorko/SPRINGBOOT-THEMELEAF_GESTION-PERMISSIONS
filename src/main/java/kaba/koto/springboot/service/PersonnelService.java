package kaba.koto.springboot.service;

import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.UserRepository;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Personnel;
import kaba.koto.springboot.repositories.DemandeRepository;
import kaba.koto.springboot.repositories.EmployeeRepository;
import kaba.koto.springboot.repositories.PersonnelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DemandeRepository demandeRepository;

    public List<Personnel> getPersonnelByService(kaba.koto.springboot.auth.entities.Service service) {
        return personnelRepository.findByService(service);
    }


//    public List<Personnel> getPersonnelForCurrentUser() {
//        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        User user = userRepository.findByUsername(username);;
//        return personnelRepository.findByServiceId(user.getService().getId());
//    }

//    public long countPersonnelForCurrentUser() {
//        return getPersonnelForCurrentUser().size();
//    }


//    public Page<Personnel> findEmployesByServiceId(Long serviceId, Pageable pageable) {
//        return personnelRepository.findByServiceId(serviceId, pageable);
//    }
//
//    public Page<Personnel> findAllEmployes(Pageable pageable) {
//        return personnelRepository.findAll(pageable);
//    }


    public List<Personnel> findByServiceId(Long serviceId) {
        return personnelRepository.findByServiceId(serviceId);
    }

    public List<Personnel> findAllPersonnel() {
        return personnelRepository.findAll();
    }


    public long countAllPersonnels() {
        return personnelRepository.count();
    }

    public long countPersonnelsByServiceId(Long serviceId) {
        return personnelRepository.findByServiceId(serviceId).size();
    }

    public long countByServiceId(Long serviceId) {
        return personnelRepository.findByServiceId(serviceId).size();
    }

//    public List<Personnel> getPersonnelByService(Long serviceId) {
//        return personnelRepository.findByServiceId(serviceId);
//    }

    public long countPersonnelByService(Long serviceId) {
        return personnelRepository.countByServiceId(serviceId);
    }

    public List<Personnel> getPersonnelByService(Long serviceId) {
        return personnelRepository.findByServiceId(serviceId);
    }

    public List<Personnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    public List<Personnel> getPersonnelsByService(kaba.koto.springboot.auth.entities.Service service) {
        return personnelRepository.findByService(service);
    }

    public List<Personnel> findPersonnelsByServiceId(Long serviceId) {
        return personnelRepository.findByServiceId(serviceId);
    }




}
