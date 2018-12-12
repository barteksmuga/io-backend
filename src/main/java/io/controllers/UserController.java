package io.controllers;

import io.models.Role;
import io.models.User;
import io.repositories.RoleRepository;
import io.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {




        user.setActive(false);

        Role role = new Role();

        role.setAuthorities("USER");

        String checkIsProfessor = user.getEmail().substring(user.getEmail().indexOf("@") + 1);

        if (checkIsProfessor.equals("agh.edu.pl")) {

            Role role1 = new Role();

            role1.setAuthorities("PROFESSOR");
            user.addRoles(role1);
        }

        user.addRoles(role);

        user.setHashedPassword();

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(savedUser);

    }

}
