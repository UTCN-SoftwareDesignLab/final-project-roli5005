package com.example.parking.reservation;

import com.example.parking.reservation.dto.ReservationDTO;
import com.example.parking.reservation.model.Reservation;
import com.example.parking.user.validator.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.parking.UrlMapping.*;

@RestController
@RequestMapping(RESERVATIONS)
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationDTO> getAll(){ return reservationService.getAll();}

    @GetMapping(CLIENT_RESERVATIONS+ENTITY)
    public List<ReservationDTO> getAllForUser(@PathVariable long id){return reservationService.getAllForThisUser(id); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable long id){reservationService.delete(id);}

    @PatchMapping
    public String edit(@RequestBody ReservationDTO reservationDTO) throws Exception {
        Notification<ReservationDTO> notification =  reservationService.edit(reservationDTO);
        if(notification.hasErrors()) return notification.getFormattedErrors();
        else return "Success!";
    }

    @PostMapping
    public String create(@RequestBody ReservationDTO reservationDTO) throws Exception {
       Notification<ReservationDTO> notification =  reservationService.create(reservationDTO);
       if(notification.hasErrors()) return notification.getFormattedErrors();
       else return "Success!";
    }
}
