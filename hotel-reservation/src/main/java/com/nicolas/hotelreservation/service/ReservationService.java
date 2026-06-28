package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.ReservationRequestDTO;
import com.nicolas.hotelreservation.dto.response.ReservationResponseDTO;
import com.nicolas.hotelreservation.entity.ReservationEntity;
import com.nicolas.hotelreservation.entity.RoomEntity;
import com.nicolas.hotelreservation.entity.UserEntity;
import com.nicolas.hotelreservation.enums.ReservationStatus;
import com.nicolas.hotelreservation.exception.BadRequestException;
import com.nicolas.hotelreservation.exception.NotFoundException;
import com.nicolas.hotelreservation.mapper.ReservationMapper;
import com.nicolas.hotelreservation.repository.IReservationRepository;
import com.nicolas.hotelreservation.repository.IRoomRepository;
import com.nicolas.hotelreservation.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final IReservationRepository reservationRepository;
    private final IRoomRepository roomRepository;
    private final IUserRepository userRepository;
    private final ReservationMapper reservationMapper;

    public void createReservation(ReservationRequestDTO reservationRequestDTO, String email) {
        RoomEntity room = roomRepository.findById(reservationRequestDTO.roomId()).orElseThrow(() -> new NotFoundException("Quarto não encontrado"));
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        long totalDays = reservationRequestDTO.checkInDate().toLocalDate()
                .until(reservationRequestDTO.checkOutDate().toLocalDate(), ChronoUnit.DAYS);

        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(totalDays));

        LocalDateTime checkIn = reservationRequestDTO.checkInDate();
        LocalDateTime checkOut = reservationRequestDTO.checkOutDate();

        if (checkIn.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("A data de check-in não pode ser no passado.");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new BadRequestException("A data de check-out deve ser posterior à data de check-in.");
        }

        boolean isOccupied = reservationRepository.existsConflictingReservation(room.getId(), checkIn, checkOut);
        if(isOccupied) {
            throw new BadRequestException("Este quarto já está reservado para as datas selecionadas.");
        }

        ReservationEntity reservationEntity = reservationMapper.dtoToEntity(reservationRequestDTO, room, user, totalPrice);

        reservationRepository.save(reservationEntity);
    }

    public List<ReservationResponseDTO> getAllUserReservations(String email) {
        return reservationRepository.findAllByUserEmail(email)
                .stream()
                .map(reservationMapper::entityToDto)
                .toList();
    }


    public List<ReservationResponseDTO> getAllReservations(ReservationStatus status) {
        List<ReservationEntity> reservations;

        if(status != null) {
            reservations = reservationRepository.findByStatus(status);
        } else {
            reservations = reservationRepository.findAll();
        }

        return reservations.stream().map(reservationMapper::entityToDto).toList();
    }

    public ReservationResponseDTO getReservationById(Long id) {
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada."));

        return reservationMapper.entityToDto(reservation);
    }

    public void cancelReservation(Long id) {
        ReservationEntity reservationEntity = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva não encontrada"));

        if(reservationEntity.getStatus().equals(ReservationStatus.CANCELLED))
            throw new BadRequestException("A reserva já foi cancelada");

        reservationEntity.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservationEntity);
    }
}
