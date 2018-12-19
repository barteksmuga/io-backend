package io.controllers;

import io.models.User;
import io.services.RegisterService;
import io.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


@RestController
public class UserController {

    private final RegisterService registerService;
    private final UserService userService;

    @Autowired
    public UserController(RegisterService registerService, UserService userService) {
        this.registerService = registerService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register(@RequestParam(value = "email") String email,
                                   @RequestParam(value = "password") String password) {
        try {
            if (!registerService.isPasswordValid(password)) {
                return new ResponseEntity<>("Wrong password. Password requirements: one lowercase letter, " +
                        "one uppercase letter, one digit, one of special characters(@$%^), more than 6 characters and no white spaces",
                        HttpStatus.NOT_ACCEPTABLE);
            }
            if (!registerService.isValidEmail(email)) {
                return new ResponseEntity<>("Wrong email.",
                        HttpStatus.NOT_ACCEPTABLE);
            }
            if (userService.existsLogin(email)) {
                return new ResponseEntity<>("User with that email already exists!", HttpStatus.NOT_ACCEPTABLE);
            }
            User user = new User(email);
            user.setHashedPassword(password);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
