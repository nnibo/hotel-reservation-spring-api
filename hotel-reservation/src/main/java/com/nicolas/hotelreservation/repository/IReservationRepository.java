package com.nicolas.hotelreservation.repository;

import com.nicolas.hotelreservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
