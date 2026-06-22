package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.RoomRequestDTO;
import com.nicolas.hotelreservation.dto.response.RoomResponseDTO;
import com.nicolas.hotelreservation.entity.HotelEntity;
import com.nicolas.hotelreservation.entity.RoomEntity;
import com.nicolas.hotelreservation.exception.BadRequestException;
import com.nicolas.hotelreservation.exception.NotFoundException;
import com.nicolas.hotelreservation.mapper.RoomMapper;
import com.nicolas.hotelreservation.repository.IHotelRepository;
import com.nicolas.hotelreservation.repository.IRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final IRoomRepository roomRepository;
    private final IHotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    public void createRoom(Long hotelId, RoomRequestDTO roomRequestDTO) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel não encontrado para vinculação"));

        Optional<RoomEntity> roomWithSameNumber = roomRepository.findByRoomNumberAndHotelId(roomRequestDTO.roomNumber(), hotel.getId());

        if(roomWithSameNumber.isPresent()) {
            throw new BadRequestException("Já existe um quarto com o número " + roomRequestDTO.roomNumber() + "no hotel " + hotel.getName());
        }

        RoomEntity newRoom = roomMapper.dtoToEntity(roomRequestDTO, hotel);
        roomRepository.save(newRoom);
    }

    public List<RoomResponseDTO> getAllRoomByHotelId(Long hotelId) {
        hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        return roomRepository.findAllByHotelId(hotelId)
                .stream()
                .map(roomMapper::entityToDto)
                .toList();
    }

    public RoomResponseDTO getRoomById(Long roomId) {
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Quarto não encontrado"));

        return roomMapper.entityToDto(room);
    }

    public RoomResponseDTO updateRoom(Long roomId, RoomRequestDTO roomDTO) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Quarto não encontrado"));

        Optional<RoomEntity> roomWithSameNumber = roomRepository.findByRoomNumberAndHotelId(roomDTO.roomNumber(), room.getHotel().getId());

        if (roomWithSameNumber.isPresent() && !roomWithSameNumber.get().getId().equals(roomId)) {
            throw new BadRequestException("Já existe um quarto com o número " + roomDTO.roomNumber() + " neste hotel.");
        }

        roomMapper.updateEntityFromDTO(room, roomDTO);
        roomRepository.save(room);
        return roomMapper.entityToDto(room);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Quarto não encontrado"));

        roomRepository.deleteById(roomId);
    }
}
