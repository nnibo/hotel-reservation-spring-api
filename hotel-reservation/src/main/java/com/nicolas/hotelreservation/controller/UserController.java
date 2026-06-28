package com.nicolas.hotelreservation.controller;

import com.nicolas.hotelreservation.dto.request.UserRequestDTO;
import com.nicolas.hotelreservation.dto.response.UserResponseDTO;
import com.nicolas.hotelreservation.enums.UserRole;
import com.nicolas.hotelreservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> getAllUsers(@RequestParam(required = false) UserRole role) {
        return userService.getAllUsers(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}/promote")
    @ResponseStatus(HttpStatus.OK)
    public void promoteUserToAdmin(@PathVariable Long userId) {
        userService.promoteUserToAdmin(userId);
    }

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateUserById(@PathVariable Long userId, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return userService.updateUser(userId, userRequestDTO);
    }

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
