package kaba.koto.springboot.repositories;

import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.entities.Personnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    //
    List<Personnel> findByServiceId(Long serviceId);
    List<Personnel> findByService(Service service);
    long countByServiceId(Long serviceId);


}
