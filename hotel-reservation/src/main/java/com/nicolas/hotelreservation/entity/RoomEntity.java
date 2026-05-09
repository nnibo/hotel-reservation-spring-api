package com.nicolas.hotelreservation.entity;

import com.nicolas.hotelreservation.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"room_number", "hotel_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;
    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Column(name = "price_per_night", nullable = false)
    private BigDecimal pricePerNight;
    @Column(name = "max_guests", nullable = false)
    private Integer maxGuests;

    @OneToMany(mappedBy = "room")
    @Builder.Default
    private List<ReservationEntity> reservations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;
}
