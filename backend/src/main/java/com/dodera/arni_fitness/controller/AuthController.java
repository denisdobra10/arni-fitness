package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.request.LoginRequest;
import com.dodera.arni_fitness.dto.response.LoginResponse;
import com.dodera.arni_fitness.dto.SignUpRequest;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.security.TokenService;
import com.dodera.arni_fitness.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class AuthController {
    private final AuthenticationService authService;
    private final TokenService tokenService;

    public AuthController(AuthenticationService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        User loggedInUser = authService.loginUser(loginRequest.email(), loginRequest.password());
        String jwtToken = tokenService.generateToken(loginRequest.email());

        return new ResponseEntity<>(new LoginResponse(jwtToken), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User registeredUser = authService.registerUser(signUpRequest);

        String jwtToken = tokenService.generateToken(registeredUser.getEmail());

        return new ResponseEntity<>(new LoginResponse(jwtToken), HttpStatus.OK);

    }
}


