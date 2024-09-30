package kaba.koto.springboot.entities;

import jakarta.persistence.*;
import kaba.koto.springboot.auth.entities.Service;
import kaba.koto.springboot.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "demande_id")
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(columnDefinition = "Integer default 0")
    private int statut;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePermission;

}
