package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
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
    private String accountId;
    private String serviceId;
    private Status status;
    private String location;
}
