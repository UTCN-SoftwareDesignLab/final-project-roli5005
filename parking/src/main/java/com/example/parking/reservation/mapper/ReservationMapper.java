package com.example.parking.reservation.mapper;

import com.example.parking.reservation.dto.ReservationDTO;
import com.example.parking.reservation.model.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO toDTO(Reservation reservation);

    Reservation fromDTO(ReservationDTO reservationDTO);
}
