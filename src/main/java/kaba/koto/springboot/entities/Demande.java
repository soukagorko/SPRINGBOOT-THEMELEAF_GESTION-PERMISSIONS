package kaba.koto.springboot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kaba.koto.springboot.auth.entities.Role;
import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.entities.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "demandes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Demande {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "destinataire_demande", length = 255, nullable = false)
    private String destinataireDemande;

    @Column(name = "duree_permission")
    private int dureePermission;

    @Column(name="lieu", length = 50, nullable = true)
    private String lieu;

    @Column(name = "date_debut", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;

    @Column(name = "date_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    @Column(name = "objet", length = 100, nullable = true)
    private String objet;

    @Column(name = "motif", length = 50, nullable = false)
    private String motif;

    @Column(name = "destination", length = 50, nullable = false)
    private String destination;
    //
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //
    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private Personnel personnel;
    //
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
    //
    @Column(columnDefinition = "Integer default 0")
    private Integer statut;
    //
    private LocalDateTime createdAt;

    private LocalDateTime dateDemande;



}
