package io.controllers;


import io.models.User;

import io.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private CustomerService customerService;
    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        logger.info("Sing up User, email:[" + user.getEmail() + "]");
        User savedUser = customerService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

}
