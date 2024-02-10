package com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer;

import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityRequestModel;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityResponseModel;

import java.util.List;

public interface AvailabilityService {

    List<AvailabilityResponseModel> getAllAvailabilities(String dayOfWeek);
    AvailabilityResponseModel addAvailability(String dayOfWeek, AvailabilityRequestModel availabilityRequestModel);
    void deleteAvailability(String availabilityId);
}
