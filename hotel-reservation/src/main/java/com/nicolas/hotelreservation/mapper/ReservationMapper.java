package com.nicolas.hotelreservation.mapper;

import com.nicolas.hotelreservation.dto.request.ReservationRequestDTO;
import com.nicolas.hotelreservation.dto.response.ReservationResponseDTO;
import com.nicolas.hotelreservation.entity.ReservationEntity;
import com.nicolas.hotelreservation.entity.RoomEntity;
import com.nicolas.hotelreservation.entity.UserEntity;
import com.nicolas.hotelreservation.enums.ReservationStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReservationMapper {

    public ReservationResponseDTO entityToDto(ReservationEntity reservationEntity) {
        return new ReservationResponseDTO(
                reservationEntity.getId(),
                reservationEntity.getCheckInDate(),
                reservationEntity.getCheckOutDate(),
                reservationEntity.getTotalPrice(),
                reservationEntity.getStatus(),
                reservationEntity.getUser().getId(),
                reservationEntity.getUser().getName(),
                reservationEntity.getRoom().getId(),
                reservationEntity.getRoom().getRoomNumber(),
                reservationEntity.getRoom().getHotel().getName()
        );
    }

    public ReservationEntity dtoToEntity(ReservationRequestDTO reservationRequestDTO,
                                         RoomEntity room,
                                         UserEntity user,
                                         BigDecimal totalPrice) {
        return ReservationEntity.builder()
                .checkInDate(reservationRequestDTO.checkInDate())
                .checkOutDate(reservationRequestDTO.checkOutDate())
                .status(ReservationStatus.CONFIRMED)
                .room(room)
                .user(user)
                .totalPrice(totalPrice)
                .build();
    }
}
