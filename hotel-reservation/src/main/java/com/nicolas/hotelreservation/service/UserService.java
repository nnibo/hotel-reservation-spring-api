package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.UserRequestDTO;
import com.nicolas.hotelreservation.dto.response.UserResponseDTO;
import com.nicolas.hotelreservation.entity.UserEntity;
import com.nicolas.hotelreservation.enums.UserRole;
import com.nicolas.hotelreservation.exception.BadRequestException;
import com.nicolas.hotelreservation.exception.NotFoundException;
import com.nicolas.hotelreservation.mapper.UserMapper;
import com.nicolas.hotelreservation.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers(UserRole role) {
        List<UserEntity> users;

        if (role != null) {
            users = userRepository.findByRole(role);
        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(userMapper::entityToDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        return userMapper.entityToDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (!user.getEmail().equals(userDTO.email())) {
            if (userRepository.existsByEmail(userDTO.email())) {
                throw new BadRequestException("Este e-mail já está sendo usado por outro usuário.");
            }
        }

        userMapper.updateEntityFromDTO(user, userDTO);
        UserEntity updatedUser = userRepository.save(user);

        return userMapper.entityToDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);
    }

    public void promoteUserToAdmin(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if(user.getRole() == UserRole.ROLE_ADMIN) {
            throw new BadRequestException("Esse usuário já é um Admin");
        }

        user.setRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
    }
}
