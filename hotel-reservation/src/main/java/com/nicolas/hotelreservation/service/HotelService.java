package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.HotelRequestDTO;
import com.nicolas.hotelreservation.dto.response.HotelResponseDTO;
import com.nicolas.hotelreservation.entity.HotelEntity;
import com.nicolas.hotelreservation.repository.IHotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final IHotelRepository hotelRepository;

    public void createHotel(HotelRequestDTO hotelDTO) throws Exception{
        HotelEntity hotel = hotelRepository.findByName(hotelDTO.name()).orElse(null);

        if(hotel != null){
            throw new Exception("Hotel já cadastrado no sistema");
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

    public HotelResponseDTO getHotelById(Long id) throws Exception{
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new Exception("Hotel não encontrado"));

        return new HotelResponseDTO(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getCity(), hotel.getAddress());
    }
}
