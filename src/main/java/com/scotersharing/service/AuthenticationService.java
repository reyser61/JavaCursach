package com.scotersharing.service;

import com.scotersharing.dto.request.AuthenticationRequest;
import com.scotersharing.dto.request.RegisterRequest;
import com.scotersharing.dto.response.AuthenticationResponse;
import com.scotersharing.entity.User;
import com.scotersharing.exception.BadRequestException;
import com.scotersharing.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public void register(RegisterRequest request) {
        String email = request.getEmail();
        String phone = request.getPhone();

        if(userService.existsUserByEmail(email)){
            throw new BadRequestException("The email is already used");
        }
        if(userService.existsByPhone(phone)){
            throw new BadRequestException("The phone is already used" );
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        System.out.println("Registering user: " + user);
        System.out.println("drivingExperience: " + request.getDrivingExperience());
        userService.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new UnauthorizedException("Username or password is wrong");
        }

        var user = userDetailService.loadUserByUsername(request.getEmail());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
