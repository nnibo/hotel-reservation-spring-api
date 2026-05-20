package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.RoomRequestDTO;
import com.nicolas.hotelreservation.dto.response.RoomResponseDTO;
import com.nicolas.hotelreservation.entity.HotelEntity;
import com.nicolas.hotelreservation.entity.RoomEntity;
import com.nicolas.hotelreservation.exception.NotFoundException;
import com.nicolas.hotelreservation.mapper.RoomMapper;
import com.nicolas.hotelreservation.repository.IHotelRepository;
import com.nicolas.hotelreservation.repository.IRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final IRoomRepository roomRepository;
    private final IHotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    public void createRoom(Long hotelId, RoomRequestDTO roomRequestDTO) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel não encontrado para vinculação"));

        RoomEntity newRoom = roomMapper.dtoToEntity(roomRequestDTO);
        roomRepository.save(newRoom);
    }

    public List<RoomResponseDTO> getAllRoomByHotelId(Long hotelId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        return roomRepository.findAllByHotelId(hotelId)
                .stream()
                .map(roomMapper::entityToDto)
                .toList();
    }

    public RoomResponseDTO getRoomById(Long hotelId, Long roomId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Quarto não encontrado"));

        return roomMapper.entityToDto(room);
    }

    public RoomResponseDTO updateRoom(Long roomId, RoomRequestDTO roomDTO) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Quarto não encontrado"));

        roomMapper.updateEntityFromDTO(room, roomDTO);

        RoomEntity updatedRoom = roomRepository.save(room);
        return roomMapper.entityToDto(updatedRoom);
    }

    public void deleteRoom(Long roomId) {
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Quarto não encontrado"));

        roomRepository.deleteById(roomId);
    }
}
