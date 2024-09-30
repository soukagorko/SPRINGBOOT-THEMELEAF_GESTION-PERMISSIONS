package kaba.koto.springboot.auth.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kaba.koto.springboot.entities.Demande;
import kaba.koto.springboot.entities.Personnel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nomService", length = 50, nullable = true)
    private String nomService;
    @Column(name = "sigleService", length = 20, nullable = false)
    private String sigleService;
    @Column(name = "descService", length = 100, nullable = false)
    private String descService;
    @Column(name = "contactService", length = 30, nullable = true)
    private String contactService;
    @Column(name = "nomChefService", length = 50, nullable = true)
    private String nomChefService;
    @Column(name = "gradeChefService", length = 50, nullable = true)
    private String gradeChefService;
    @Column(name = "fonctionChefService", length = 100, nullable = true)
    private String fonctionChefService;
    @Column(name = "statutChefService", length = 30, nullable = true)
    private String statutChefService;

}
