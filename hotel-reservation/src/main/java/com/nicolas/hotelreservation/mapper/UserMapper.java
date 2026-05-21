package com.nicolas.hotelreservation.mapper;

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

    public UserEntity dtoToEntity(UserRequestDTO userRequestDTO) {
        return UserEntity.builder()
                .name(userRequestDTO.name())
                .email(userRequestDTO.email())
                .phoneNumber(userRequestDTO.phoneNumber())
                .build();
    }

    public void updateEntityFromDTO(UserEntity userEntity, UserRequestDTO userRequestDTO) {
        userEntity.setName(userRequestDTO.name());
        userEntity.setEmail(userRequestDTO.email());
        userEntity.setPhoneNumber(userRequestDTO.phoneNumber());
    }
}
