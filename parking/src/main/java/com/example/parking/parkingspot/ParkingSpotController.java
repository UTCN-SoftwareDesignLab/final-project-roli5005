package com.example.parking.parkingspot;

import com.example.parking.parkingspot.dto.SpotDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.parking.UrlMapping.*;

@RestController
@RequestMapping(PARKING)
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @GetMapping
    public List<SpotDTO> getAll(){return parkingSpotService.getAll();}

    @GetMapping(CLIENT_RESERVATIONS)
    public List<String> getAllNumbers() {return parkingSpotService.getParkingNumbers(); }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable long id){parkingSpotService.delete(id);}

    @PostMapping
    public SpotDTO create(@RequestBody SpotDTO spotDTO){ return parkingSpotService.create(spotDTO);}

    @PatchMapping
    public SpotDTO edit(@RequestBody SpotDTO spotDTO){ return parkingSpotService.edit(spotDTO); }

}
