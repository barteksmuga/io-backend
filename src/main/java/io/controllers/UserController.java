package io.controllers;

import io.models.Role;
import io.models.User;
import io.repositories.RoleRepository;
import io.repositories.UserRepository;
import io.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;



    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

        
        User savedUser=customerService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

}
