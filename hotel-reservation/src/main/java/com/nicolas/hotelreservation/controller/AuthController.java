package com.nicolas.hotelreservation.controller;

import com.nicolas.hotelreservation.dto.request.LoginRequestDTO;
import com.nicolas.hotelreservation.dto.request.RegisterRequestDTO;
import com.nicolas.hotelreservation.dto.response.JwtResponseDTO;
import com.nicolas.hotelreservation.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        authService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }
}
