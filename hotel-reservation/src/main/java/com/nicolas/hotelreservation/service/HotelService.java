package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.HotelRequestDTO;
import com.nicolas.hotelreservation.dto.response.HotelResponseDTO;
import com.nicolas.hotelreservation.entity.HotelEntity;
import com.nicolas.hotelreservation.exception.BadRequestException;
import com.nicolas.hotelreservation.exception.NotFoundException;
import com.nicolas.hotelreservation.repository.IHotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final IHotelRepository hotelRepository;

    public void createHotel(HotelRequestDTO hotelDTO) {
        HotelEntity hotel = hotelRepository.findByName(hotelDTO.name()).orElse(null);

        if(hotel != null){
            throw new BadRequestException("Hotel já cadastrado no sistema");
        }

        hotelRepository.save(HotelEntity.builder()
                .name(hotelDTO.name())
                .description(hotelDTO.description())
                .city(hotelDTO.city())
                .address(hotelDTO.address())
                .build()
        );
    }

    public List<HotelResponseDTO> getAllHotels() {
        List<HotelEntity> hotels = hotelRepository.findAll();

        return hotels.stream().map(
                hotel -> new HotelResponseDTO(
                                                hotel.getId(),
                                                hotel.getName(),
                                                hotel.getDescription(),
                                                hotel.getCity(),
                                                hotel.getAddress()))
                                        .toList();
    }

    public HotelResponseDTO getHotelById(Long id) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        return new HotelResponseDTO(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getCity(), hotel.getAddress());
    }

    public HotelResponseDTO updateHotelById(Long id, HotelRequestDTO hotelRequestDTO) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        hotel.setName(hotelRequestDTO.name());
        hotel.setDescription(hotelRequestDTO.description());
        hotel.setCity(hotelRequestDTO.city());
        hotel.setAddress(hotelRequestDTO.address());

        HotelEntity updatedHotel = hotelRepository.save(hotel);

        return new HotelResponseDTO(
                updatedHotel.getId(),
                updatedHotel.getName(),
                updatedHotel.getDescription(),
                updatedHotel.getCity(),
                updatedHotel.getAddress()
        );
    }

    public void deleteHotel(Long id) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        hotelRepository.delete(hotel);
    }
}
