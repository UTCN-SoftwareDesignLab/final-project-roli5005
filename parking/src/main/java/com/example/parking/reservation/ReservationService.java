package com.example.parking.reservation;


import com.example.parking.reservation.dto.ReservationDTO;
import com.example.parking.reservation.mapper.ReservationMapperImplemented;
import com.example.parking.reservation.model.Reservation;
import com.example.parking.user.UserRepository;
import com.example.parking.user.validator.Notification;
import com.example.parking.websocket.OutputMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationMapperImplemented reservationMapper;
    private final EmailService emailService;
    private final double fee = 7.0;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public List<ReservationDTO> getAll(){
        return reservationRepository.findAll()
                .stream().map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getAllForThisUser(long id){
        return reservationRepository.findAllByUser_id(id)
                .stream().map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Notification<ReservationDTO> create(ReservationDTO reservationDTO) throws Exception {
        Notification<ReservationDTO> notification = new Notification<>();
        LocalDateTime date = LocalDateTime.parse(reservationDTO.getDate()+" "+reservationDTO.getTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if(reservationRepository.findByDateAndAndUser_id(date,reservationDTO.getUserId()).isPresent())
            notification.addError("User already has a reservation at that time");
        if(reservationRepository.findByDateAndSpotNumber(date,reservationDTO.getSpotNumber()).isPresent())
            notification.addError("That spot is occupied for the selected date");
        if(!notification.hasErrors())
        {
            reservationDTO.setFee(calculateFee(reservationDTO.getUserId(),date));
            notification.setResult( reservationMapper.toDTO(reservationRepository.save(reservationMapper.fromDTO(reservationDTO))));
            String email = userRepository.findById(reservationDTO.getUserId()).get().getEmail();
            emailService.sendConfirmation(email,"Reservation confirmation",reservationDTO);
            OutputMessage message = new OutputMessage();
            message.buildMessage(reservationDTO);
            simpMessageSendingOperations.convertAndSend("/users",message.getMessage());
        }
        return notification;
    }

    public void delete(long id){
        reservationRepository.deleteById(id);
    }


    public Notification<ReservationDTO> edit(ReservationDTO reservationDTO) throws Exception {
        Notification<ReservationDTO> notification = new Notification<>();
        LocalDateTime date = LocalDateTime.parse(reservationDTO.getDate()+" "+reservationDTO.getTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if(reservationRepository.findByDateAndSpotNumber(date,reservationDTO.getSpotNumber()).isPresent())
            notification.addError("That spot is occupied for the selected date");
        if(!notification.hasErrors())
        {
            reservationDTO.setFee(calculateFee(reservationDTO.getUserId(),date));
            delete(reservationDTO.getId());
            notification.setResult( reservationMapper.toDTO(reservationRepository.save(reservationMapper.fromDTO(reservationDTO))));
            String email = userRepository.findById(reservationDTO.getUserId()).get().getEmail();
            emailService.sendUpdate(email,"Reservation update",reservationDTO);
        }
        return notification;
    }

    private double calculateFee(long userID, LocalDateTime date){
        List<Reservation> clientReservations = reservationRepository.findAllByUser_id(userID);
        int currentMonth = date.getMonthValue();
        int numberOfReservations = (int) clientReservations.stream().filter(reservation -> reservation.getDate().getMonthValue() == currentMonth).count();
        if(numberOfReservations >= 10) return fee*4/5;
        else return fee;
    }

}
