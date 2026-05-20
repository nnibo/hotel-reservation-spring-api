package com.nicolas.hotelreservation.mapper;

import com.nicolas.hotelreservation.dto.request.RoomRequestDTO;
import com.nicolas.hotelreservation.dto.response.RoomResponseDTO;
import com.nicolas.hotelreservation.entity.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomResponseDTO entityToDto(RoomEntity roomEntity) {
        return new RoomResponseDTO(
                roomEntity.getId(),
                roomEntity.getRoomNumber(),
                roomEntity.getRoomType(),
                roomEntity.getPricePerNight(),
                roomEntity.getMaxGuests()
        );
    }

    public RoomEntity dtoToEntity(RoomRequestDTO roomRequestDTO) {
        return RoomEntity.builder()
                .roomNumber(roomRequestDTO.roomNumber())
                .roomType(roomRequestDTO.roomType())
                .pricePerNight(roomRequestDTO.pricePerNight())
                .maxGuests(roomRequestDTO.maxGuests())
                .build();
    }

    public void updateEntityFromDTO(RoomEntity roomEntity, RoomRequestDTO roomRequestDTO) {
        roomEntity.setRoomNumber(roomRequestDTO.roomNumber());
        roomEntity.setRoomType(roomRequestDTO.roomType());
        roomEntity.setPricePerNight(roomRequestDTO.pricePerNight());
        roomEntity.setMaxGuests(roomRequestDTO.maxGuests());
    }
}
