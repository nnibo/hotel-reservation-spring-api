package com.nicolas.hotelreservation.service;

import com.nicolas.hotelreservation.dto.request.HotelRequestDTO;
import com.nicolas.hotelreservation.dto.response.HotelResponseDTO;
import com.nicolas.hotelreservation.entity.HotelEntity;
import com.nicolas.hotelreservation.exception.BadRequestException;
import com.nicolas.hotelreservation.exception.NotFoundException;
import com.nicolas.hotelreservation.mapper.HotelMapper;
import com.nicolas.hotelreservation.repository.IHotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final IHotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public void createHotel(HotelRequestDTO hotelDTO) {
        HotelEntity hotel = hotelRepository.findByName(hotelDTO.name()).orElse(null);

        if(hotel != null){
            throw new BadRequestException("Hotel já cadastrado no sistema");
        }

        HotelEntity newHotel = hotelMapper.dtoToEntity(hotelDTO);
        hotelRepository.save(newHotel);
    }

    public List<HotelResponseDTO> getAllHotels() {
        List<HotelEntity> hotels = hotelRepository.findAll();

        return hotels.stream()
                .map(hotelMapper::entityToDto)
                .toList();
    }

    public HotelResponseDTO getHotelById(Long id) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        return hotelMapper.entityToDto(hotel);
    }

    public HotelResponseDTO updateHotelById(Long id, HotelRequestDTO hotelRequestDTO) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        hotelMapper.updateEntityFromDTO(hotelRequestDTO, hotel);
        HotelEntity updatedHotel = hotelRepository.save(hotel);

        return hotelMapper.entityToDto(updatedHotel);
    }

    public void deleteHotel(Long id) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel não encontrado"));

        hotelRepository.delete(hotel);
    }
}
