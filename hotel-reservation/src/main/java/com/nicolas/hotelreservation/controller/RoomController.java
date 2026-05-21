package com.nicolas.hotelreservation.controller;

import com.nicolas.hotelreservation.dto.request.RoomRequestDTO;
import com.nicolas.hotelreservation.dto.response.RoomResponseDTO;
import com.nicolas.hotelreservation.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/hotels/{hotelId}/rooms")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponseDTO> getAllRooms(@PathVariable Long hotelId) {
        return roomService.getAllRoomByHotelId(hotelId);
    }

    @GetMapping("/hotels/{hotelId}/rooms/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponseDTO getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        return roomService.getRoomById(hotelId, roomId);
    }

    @PostMapping("/hotels/{hotelId}/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoom(@PathVariable Long hotelId, @RequestBody @Valid RoomRequestDTO roomRequestDTO) {
        roomService.createRoom(hotelId, roomRequestDTO);
    }

    @PutMapping("/rooms/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponseDTO updateRoom(@PathVariable Long roomId, @RequestBody @Valid RoomRequestDTO roomRequestDTO) {
        return roomService.updateRoom(roomId, roomRequestDTO);
    }

    @DeleteMapping("/rooms/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
    }

}
