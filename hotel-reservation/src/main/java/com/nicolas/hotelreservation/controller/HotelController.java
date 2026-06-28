package com.nicolas.hotelreservation.controller;

import com.nicolas.hotelreservation.dto.request.HotelRequestDTO;
import com.nicolas.hotelreservation.dto.response.HotelResponseDTO;
import com.nicolas.hotelreservation.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponseDTO> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponseDTO getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHotel(@RequestBody @Valid HotelRequestDTO hotelDTO) {
        hotelService.createHotel(hotelDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponseDTO updateHotel(@PathVariable Long id, @RequestBody @Valid HotelRequestDTO hotelRequestDTO) {
        return hotelService.updateHotelById(id, hotelRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }
}
