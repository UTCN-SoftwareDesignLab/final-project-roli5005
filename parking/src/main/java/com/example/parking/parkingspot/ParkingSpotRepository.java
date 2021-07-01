package com.example.parking.parkingspot;

import com.example.parking.parkingspot.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Long> {
}
