package com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer;

import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityResponseModel;

import java.util.List;

public interface AvailabilityService {

    List<AvailabilityResponseModel> getAllAppointments(String date);
}
