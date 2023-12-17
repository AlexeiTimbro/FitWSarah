package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AppointmentResponseModel {
    private String appointmentId;
    private String availabilityId;
    private String accountId;
    private String adminId;
    private String serviceId;
    private String status;
    private String location;
}
