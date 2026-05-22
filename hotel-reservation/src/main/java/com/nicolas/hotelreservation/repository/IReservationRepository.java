package com.nicolas.hotelreservation.repository;

import com.nicolas.hotelreservation.entity.ReservationEntity;
import com.nicolas.hotelreservation.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAllByStatus(ReservationStatus reservationStatus);

    @Query("SELECT COUNT(r) > 0 FROM ReservationEntity r " +
            "WHERE r.room.id = :roomId " +
            "AND r.checkInDate < :checkOutDate " +
            "AND r.checkOutDate > :checkInDate " +
            "AND r.status != 'CANCELLED'")
    boolean existsConflictingReservation(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDateTime checkInDate,
            @Param("checkOutDate") LocalDateTime checkOutDate
    );
}
