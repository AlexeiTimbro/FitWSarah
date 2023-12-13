package com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth0")
public class Auth0Controller {

    @GetMapping(value = "/public")
    public ResponseEntity<Auth0ResponseDTO> publicEndpoint() {
        return ResponseEntity.ok(new Auth0ResponseDTO("Public Endpoint Working fine !"));
    }

    @GetMapping(value = "/private")
    public ResponseEntity<Auth0ResponseDTO> privateEndpoint() {
        return ResponseEntity.ok(new Auth0ResponseDTO("Private Endpoint Working fine !"));
    }
}
