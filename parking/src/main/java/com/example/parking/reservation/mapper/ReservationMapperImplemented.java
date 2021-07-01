package com.example.parking.reservation.mapper;

import com.example.parking.reservation.dto.ReservationDTO;
import com.example.parking.reservation.model.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class ReservationMapperImplemented implements ReservationMapper {

    @Override
    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setId(reservation.getId());
        reservationDTO.setUserId(reservation.getUserId());
        reservationDTO.setSpotNumber(reservation.getSpotNumber());
        if (reservation.getDate() != null) {
            reservationDTO.setDate(reservation.getDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            reservationDTO.setTime(reservation.getDate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        reservationDTO.setFee(reservation.getFee());

        return reservationDTO;
    }

    @Override
    public Reservation fromDTO(ReservationDTO reservationDTO) {
        if (reservationDTO == null) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setId(reservationDTO.getId());
        reservation.setUserId(reservationDTO.getUserId());
        reservation.setSpotNumber(reservationDTO.getSpotNumber());
        if (reservationDTO.getDate() != null) {
            reservation.setDate(LocalDateTime.parse(reservationDTO.getDate()+" "+reservationDTO.getTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        reservation.setFee(reservationDTO.getFee());

        return reservation;
    }
}
