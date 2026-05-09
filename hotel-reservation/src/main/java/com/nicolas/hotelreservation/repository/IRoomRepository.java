package com.nicolas.hotelreservation.repository;

import com.nicolas.hotelreservation.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<RoomEntity, Long> {
}
