package com.nicolas.hotelreservation.mapper;

import com.nicolas.hotelreservation.dto.request.RegisterRequestDTO;
import com.nicolas.hotelreservation.dto.request.UserRequestDTO;
import com.nicolas.hotelreservation.dto.response.UserResponseDTO;
import com.nicolas.hotelreservation.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO entityToDTO(UserEntity userEntity) {
        return new UserResponseDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber()
        );
    }

    public UserEntity dtoToEntity(RegisterRequestDTO registerRequestDTO) {
        return UserEntity.builder()
                .name(registerRequestDTO.name())
                .email(registerRequestDTO.email())
                .phoneNumber(registerRequestDTO.phoneNumber())
                .password(registerRequestDTO.password())
                .build();
    }

    public void updateEntityFromDTO(UserEntity userEntity, UserRequestDTO userRequestDTO) {
        userEntity.setName(userRequestDTO.name());
        userEntity.setEmail(userRequestDTO.email());
        userEntity.setPhoneNumber(userRequestDTO.phoneNumber());

    }
}
