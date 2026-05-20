package com.nicolas.hotelreservation.repository;

import com.nicolas.hotelreservation.dto.response.RoomResponseDTO;
import com.nicolas.hotelreservation.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByHotelId(Long hotelId);
}
