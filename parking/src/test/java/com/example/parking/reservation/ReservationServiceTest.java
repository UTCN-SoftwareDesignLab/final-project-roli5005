package com.example.parking.reservation;

import com.example.parking.parkingspot.ParkingSpotRepository;
import com.example.parking.parkingspot.ParkingSpotService;
import com.example.parking.parkingspot.model.ParkingSpot;
import com.example.parking.reservation.dto.ReservationDTO;
import com.example.parking.reservation.model.Reservation;
import com.example.parking.user.RoleRepository;
import com.example.parking.user.UserRepository;
import com.example.parking.user.model.ERole;
import com.example.parking.user.model.Role;
import com.example.parking.user.model.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@SpringBootTest
class ReservationServiceTest {

    @MockBean
    private static ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {


    }

    @Test
    void getAll() {
        Reservation reservation = Reservation.builder()
                .date(LocalDateTime.now())
                .fee(7.6)
                .spot_id(1)
                .user_id(3)
                .build();
        reservationRepository.save(reservation);
        List<Reservation> reservationList = reservationRepository.findAll();
        Assert.isNull(reservationList);

    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void edit() {
    }
}
