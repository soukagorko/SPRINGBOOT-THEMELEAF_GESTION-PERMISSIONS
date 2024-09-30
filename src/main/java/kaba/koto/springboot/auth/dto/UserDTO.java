package kaba.koto.springboot.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String prenom;
    private String nom;
    private String contact;
    private String service;
    private String role;
    private String username;
    private String password;

}
