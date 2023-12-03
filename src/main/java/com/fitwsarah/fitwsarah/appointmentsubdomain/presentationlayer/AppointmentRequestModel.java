package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentRequestModel {
    private String availabilityId;
    private String adminId;
    private String serviceId;
    private String status;
    private String location;
}
