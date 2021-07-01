package com.example.parking.parkingspot.mapper;

import com.example.parking.parkingspot.dto.SpotDTO;
import com.example.parking.parkingspot.model.ParkingSpot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpotMapper {

    SpotDTO toDTO(ParkingSpot parkingSpot);

    ParkingSpot fromDTO(SpotDTO spotDTO);
}
