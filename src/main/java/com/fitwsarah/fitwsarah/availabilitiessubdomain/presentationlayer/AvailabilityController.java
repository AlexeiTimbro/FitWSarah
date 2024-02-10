package com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/availabilities")
public class AvailabilityController {
    private AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService){
        this.availabilityService = availabilityService;
    }
    @GetMapping()
    public List<AvailabilityResponseModel> getAllAvailabilities(@RequestParam String dayOfWeek){
        return availabilityService.getAllAvailabilities(dayOfWeek);
    }

    @PostMapping()
    public ResponseEntity<AvailabilityResponseModel> addAvailability(@RequestParam String dayOfWeek, @RequestBody AvailabilityRequestModel availabilityRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(availabilityService.addAvailability(dayOfWeek, availabilityRequestModel));
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable String availabilityId){
        availabilityService.deleteAvailability(availabilityId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

