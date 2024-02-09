package com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer.AvailabilityService;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.Availability;
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



}

