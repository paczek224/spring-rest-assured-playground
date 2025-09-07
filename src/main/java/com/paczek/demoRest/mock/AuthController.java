package com.paczek.demoRest.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/secured/basic")
    public String basic() {
        return "You are authenticated with Basic Auth!";
    }
}
