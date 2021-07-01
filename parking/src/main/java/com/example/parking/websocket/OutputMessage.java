package com.example.parking.websocket;

import com.example.parking.reservation.dto.ReservationDTO;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OutputMessage {
    private String message;

    public void buildMessage(ReservationDTO reservationDTO){
        StringBuilder stringBuilder = new StringBuilder()
                .append("New reservation:")
                .append("\n User id: ").append(reservationDTO.getUserId())
                .append("\n Date: ").append(reservationDTO.getDate())
                .append("\n Time: ").append(reservationDTO.getTime());
        message = stringBuilder.toString();
    }
}
