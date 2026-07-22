package com.nicolas.hotelreservation.repository;

import com.nicolas.hotelreservation.entity.RoomEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByHotelId(Long hotelId);

    Optional<RoomEntity> findByRoomNumberAndHotelId(Integer roomNumber, Long hotelId);
}
