package kaba.koto.springboot.repositories;

import kaba.koto.springboot.entities.Donnees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonneeRepository extends JpaRepository<Donnees, Long> {
}
