package com.nicolas.hotelreservation.repository;

import com.nicolas.hotelreservation.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IHotelRepository extends JpaRepository<HotelEntity, Long> {
    Optional<HotelEntity> findByName(String name);
}
