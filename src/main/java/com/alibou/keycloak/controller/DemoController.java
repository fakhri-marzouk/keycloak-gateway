package com.alibou.keycloak.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@SecurityRequirement(name = "keycloak")
public class DemoController {

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String hello() {
        return "Hello from Spring boot & Keycloak - USER";
    }

    @GetMapping("/hello-2")
    @PreAuthorize("hasRole('ADMIN')")
    public String hello2() {
        return "Hello from Spring boot & Keycloak - ADMIN";
    }

}
