package com.scotersharing.controller;


import com.scotersharing.dto.request.AuthenticationRequest;
import com.scotersharing.dto.request.RegisterRequest;
import com.scotersharing.dto.response.AuthenticationResponse;
import com.scotersharing.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scootersharing/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterRequest request
    ){
        authService.register(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}