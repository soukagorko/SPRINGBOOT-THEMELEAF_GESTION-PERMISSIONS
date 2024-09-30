package kaba.koto.springboot.auth.service;

import kaba.koto.springboot.auth.entities.User;
import kaba.koto.springboot.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        builder.roles(user.getRole().getNomRole());
        return builder.build();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    //
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    //
    private User user;

    public Long getId() {
        return user.getId();
    }

    public String getPrenom() {
        return user.getPrenom();
    }

    public String getNom() {
        return user.getNom();
    }

    public String getContact() {
        return user.getContact();
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getUsername();
    }

    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
//    }

//    public User getCurrentUser() {
//        // Méthode pour obtenir l'utilisateur connecté
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.findByUsername(username);
//    }




}
