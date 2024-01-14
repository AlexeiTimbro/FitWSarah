package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AppointmentRequestModel {
    private String availabilityId;
    private String userId;
    private String serviceId;
    private Status status;
    private String location;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String date;
    private String time;
}
