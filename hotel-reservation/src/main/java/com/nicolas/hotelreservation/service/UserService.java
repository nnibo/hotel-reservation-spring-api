package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.UserRequestDTO;
import com.nicolas.hotelreservation.dto.response.UserResponseDTO;
import com.nicolas.hotelreservation.entity.UserEntity;
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

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::entityToDTO).toList();
    }

    public UserResponseDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        return userMapper.entityToDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        userRepository.findByEmail(userDTO.email()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new BadRequestException("Este e-mail já está sendo usado por outro usuário.");
            }
        });

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
}
