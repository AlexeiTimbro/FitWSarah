package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentResponseModel {
    private String appointmentId;
    private String adminId;
    private String accountId;
    private String serviceId;
    private String status;
    private String location;
    private String availableStatus;
    private String availableDate;

}