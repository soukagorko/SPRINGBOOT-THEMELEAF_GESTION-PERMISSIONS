package kaba.koto.springboot.auth.repositories;

import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);
    //Optional<User> findByUsername(String username);
    //Optional<User> findByUsername(String username);


}
