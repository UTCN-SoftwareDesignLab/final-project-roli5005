package com.example.parking.reservation;

import com.example.parking.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.date = ?1 and r.spotNumber = ?2")
    Optional<Reservation> findByDateAndSpotNumber(LocalDateTime date, String spotNumber);

    @Query("SELECT r FROM Reservation r WHERE r.date = ?1 and r.userId = ?2")
    Optional<Reservation> findByDateAndAndUser_id(LocalDateTime date, long user_id);

    @Query("SELECT r from Reservation r WHERE r.userId =?1")
    List<Reservation> findAllByUser_id(long user_id);

}
