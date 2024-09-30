package kaba.koto.springboot.repositories;

import kaba.koto.springboot.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departement, Long> {
}
