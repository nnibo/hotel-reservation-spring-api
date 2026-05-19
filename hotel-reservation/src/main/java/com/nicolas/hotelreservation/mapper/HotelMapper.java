package com.nicolas.hotelreservation.mapper;

import com.nicolas.hotelreservation.dto.request.HotelRequestDTO;
import com.nicolas.hotelreservation.dto.response.HotelResponseDTO;
import com.nicolas.hotelreservation.entity.HotelEntity;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public HotelResponseDTO entityToDto(HotelEntity hotelEntity){
        return new HotelResponseDTO(
                hotelEntity.getId(),
                hotelEntity.getName(),
                hotelEntity.getDescription(),
                hotelEntity.getCity(),
                hotelEntity.getAddress()
        );
    }

    public HotelEntity dtoToEntity(HotelRequestDTO hotelRequestDTO) {
        return HotelEntity.builder()
                .name(hotelRequestDTO.name())
                .description(hotelRequestDTO.description())
                .city(hotelRequestDTO.city())
                .address(hotelRequestDTO.address())
                .build();
    }

    public void updateEntityFromDTO(HotelRequestDTO dto, HotelEntity entity) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setCity(dto.city());
        entity.setAddress(dto.address());
    }
}
