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

    public void updateEntityFromDTO(HotelRequestDTO hotelRequestDTO, HotelEntity hotelEntity) {
        hotelEntity.setName(hotelRequestDTO.name());
        hotelEntity.setDescription(hotelRequestDTO.description());
        hotelEntity.setCity(hotelRequestDTO.city());
        hotelEntity.setAddress(hotelRequestDTO.address());
    }
}
