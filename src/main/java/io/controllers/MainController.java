package io.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home () {
        return "Hello World!";
    }
}
