package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.config.SecurityConfiguration;
import com.nicolas.hotelreservation.config.TokenProvider;
import com.nicolas.hotelreservation.dto.request.LoginRequestDTO;
import com.nicolas.hotelreservation.dto.request.RegisterRequestDTO;
import com.nicolas.hotelreservation.dto.response.JwtResponseDTO;
import com.nicolas.hotelreservation.entity.UserEntity;
import com.nicolas.hotelreservation.enums.UserRole;
import com.nicolas.hotelreservation.exception.BadRequestException;
import com.nicolas.hotelreservation.mapper.UserMapper;
import com.nicolas.hotelreservation.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${jwt.expiration-days}")
    private long expirationTime;

    public void register(RegisterRequestDTO registerRequestDTO) {
        UserEntity user = userRepository.findByEmail(registerRequestDTO.email()).orElse(null);

        if(user != null) {
            throw new BadRequestException("Email já cadastrado no sistema");
        }

        UserEntity newUser = userMapper.dtoToEntity(registerRequestDTO);
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        newUser.setRole(UserRole.ROLE_USER);
        userRepository.save(newUser);
    }

    public JwtResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            UsernamePasswordAuthenticationToken loginCredentials = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
            Authentication authentication = authenticationManager.authenticate(loginCredentials);
            String token = tokenProvider.generateToken(authentication);
            return new JwtResponseDTO(token, expirationTime);
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Credenciais inválidas");
        } catch (Exception e) {
            throw e;
        }
    }
}
