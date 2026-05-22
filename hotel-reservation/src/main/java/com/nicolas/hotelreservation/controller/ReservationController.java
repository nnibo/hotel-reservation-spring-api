package com.nicolas.hotelreservation.controller;

import com.nicolas.hotelreservation.dto.request.ReservationRequestDTO;
import com.nicolas.hotelreservation.dto.response.ReservationResponseDTO;
import com.nicolas.hotelreservation.enums.ReservationStatus;
import com.nicolas.hotelreservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@RequestBody @Valid ReservationRequestDTO reservationRequestDTO) {
        reservationService.createReservation(reservationRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping(params = "status")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponseDTO> getReservationByStatus(@RequestParam(required = false)ReservationStatus status) {
        return reservationService.getReservationsByStatus(status);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponseDTO getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

}
