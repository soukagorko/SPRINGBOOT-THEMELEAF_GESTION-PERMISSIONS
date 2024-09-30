package kaba.koto.springboot.auth.repositories;

import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository <Service, Long> {
//    Page<Service> findByNomContains(String kw, Pageable pageable);

    List<Service> findByNomService(String nomService);


    
}
