package kaba.koto.springboot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kaba.koto.springboot.auth.entities.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "personnels")
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "matricule", length = 20, nullable = true)
    private String matricule;
    @Column(name = "prenom", length = 50, nullable = false)
    private String prenom;
    @Column(name = "nom", length = 30, nullable = false)
    private String nom;
    @Column(name = "grade", length = 50, nullable = true)
    private String grade;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "contact", length = 30, nullable = true)
    private String contact;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateArrivee;

    @Column(columnDefinition = "Integer default 0")
    private String statut;

}
