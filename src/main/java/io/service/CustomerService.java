package io.service;

import io.models.Role;
import io.models.User;
import io.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){this.passwordEncoder=passwordEncoder;}

    public User addUser(User user){

        user.setActive(false);

        Role role = new Role();

        if (user.getEmail().substring(user.getEmail().indexOf("@") + 1).equals("agh.edu.pl")) {

            Role role1 = new Role();

            role1.setAuthorities("PROFESSOR");
            user.addRoles(role1);
        }

        user.addRoles(role);


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(String email){return userRepository.findUserByEmail(email);}

//    private void setDefaultUser(){
//        if (userRepository.count() == 0) {
//            User user=new User("admin@agh.edu.pl");
//
//            Role role= new Role();
//            role.setAuthorities("ADMIN");
//            Role role1=new Role();
//            user.addRoles(role);
//            user.addRoles(role1);
//            userRepository.save(user);
//        }
//    }



}
