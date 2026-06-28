package com.nicolas.hotelreservation.controller;

import com.nicolas.hotelreservation.dto.request.ReservationRequestDTO;
import com.nicolas.hotelreservation.dto.response.ReservationResponseDTO;
import com.nicolas.hotelreservation.enums.ReservationStatus;
import com.nicolas.hotelreservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@RequestBody @Valid ReservationRequestDTO reservationRequestDTO, Authentication authentication) {
        reservationService.createReservation(reservationRequestDTO, authentication.getName());
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDTO> getMyReservations(Authentication authentication){
        return reservationService.getAllUserReservations(authentication.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDTO> getAllReservations(@RequestParam(required = false)ReservationStatus status) {
        return reservationService.getAllReservations(status);
    }

    @PreAuthorize("@reservationSecurity.isOwner(#id, authentication) or hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDTO getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PreAuthorize("@reservationSecurity.isOwner(#id, authenticaton) or hasRole('ADMIN')")
    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

}
