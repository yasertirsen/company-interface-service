package com.fyp.companyinterfaceservice.controller;


import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.dto.AuthenticationResponse;
import com.fyp.companyinterfaceservice.dto.LoginRequest;
import com.fyp.companyinterfaceservice.dto.RefreshTokenRequest;
import com.fyp.companyinterfaceservice.dto.RegisterRequest;
import com.fyp.companyinterfaceservice.model.Company;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class CompanyController {

    private final ProgradClient client;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return client.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return client.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return client.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return client.logout(refreshTokenRequest);
    }

    @GetMapping("/all")
    public List<Company> getAllStudents() {
        return client.getAllStudents();
    }
}
