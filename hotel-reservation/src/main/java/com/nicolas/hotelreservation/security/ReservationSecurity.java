package com.nicolas.hotelreservation.security;

import com.nicolas.hotelreservation.exception.NotFoundException;
import com.nicolas.hotelreservation.repository.IReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("reservationSecurity")
@RequiredArgsConstructor
public class ReservationSecurity {

    private final IReservationRepository reservationRepository;

    public boolean isOwner(Long reservationId, Authentication authentication) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> reservation.getUser().getEmail().equals(authentication.getName()))
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada"));
    }
}
