package com.example.parking;


import com.example.parking.parkingspot.ParkingSpotRepository;
import com.example.parking.parkingspot.model.ParkingSpot;
import com.example.parking.reservation.ReservationRepository;
import com.example.parking.reservation.model.Reservation;
import com.example.parking.security.AuthService;
import com.example.parking.security.dto.SignupRequest;
import com.example.parking.user.RoleRepository;
import com.example.parking.user.UserRepository;
import com.example.parking.user.model.ERole;
import com.example.parking.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final ReservationRepository reservationRepository;

    private final ParkingSpotRepository parkingSpotRepository;


    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            reservationRepository.deleteAll();
            parkingSpotRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            for(int i=1;i<=20;i++){
                ParkingSpot spot = ParkingSpot.builder().number("P"+i).build();
                parkingSpotRepository.save(spot);
            }
            authService.register(SignupRequest.builder()
                    .email("roland@email.com")
                    .username("Roland")
                    .password("WooHoo1!")
                    .roles("ADMIN")
                    .build());
            authService.register(SignupRequest.builder()
                    .email("roland1@email.com")
                    .password("WooHoo1!")
                    .username("Mike")
                    .roles("EMPLOYEE")
                    .build());
            authService.register(SignupRequest.builder()
                    .email("roland3@email.com")
                    .password("WooHoo1!")
                    .username("Jessy")
                    .roles("CLIENT")
                    .build());
        }
    }
}
