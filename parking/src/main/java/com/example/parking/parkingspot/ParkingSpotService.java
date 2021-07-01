package com.example.parking.parkingspot;

import com.example.parking.parkingspot.dto.SpotDTO;
import com.example.parking.parkingspot.mapper.SpotMapper;
import com.example.parking.parkingspot.model.ParkingSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;
    private final SpotMapper spotMapper;

    public List<SpotDTO> getAll(){
        return parkingSpotRepository.findAll().stream()
                .map(spotMapper::toDTO).collect(Collectors.toList());
    }

    public List<Long> getParkingIDs(){
        return parkingSpotRepository.findAll().stream()
                .map(ParkingSpot::getId).collect(Collectors.toList());
    }

    public List<String> getParkingNumbers(){
        return parkingSpotRepository.findAll().stream()
                .map(ParkingSpot::getNumber).collect(Collectors.toList());
    }

    public SpotDTO create(SpotDTO spotDTO){
        return spotMapper.toDTO( parkingSpotRepository.save(spotMapper.fromDTO(spotDTO)));
    }

    public SpotDTO edit(SpotDTO spotDTO){
        return spotMapper.toDTO( parkingSpotRepository.save(spotMapper.fromDTO(spotDTO)));
    }
    public void delete(long id){
        parkingSpotRepository.deleteById(id);
    }

}
