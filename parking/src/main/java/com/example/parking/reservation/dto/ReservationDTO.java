package com.example.parking.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private long id;
    private long userId;
    private String spotNumber;
    private String date;
    private String time;
    private double fee;
}
