package com.nicolas.hotelreservation.controller;

import com.nicolas.hotelreservation.dto.request.UserRequestDTO;
import com.nicolas.hotelreservation.dto.response.UserResponseDTO;
import com.nicolas.hotelreservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateUserById(@PathVariable Long userId, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return userService.updateUser(userId, userRequestDTO);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
