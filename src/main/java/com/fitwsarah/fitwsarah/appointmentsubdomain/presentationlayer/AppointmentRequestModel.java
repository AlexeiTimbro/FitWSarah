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

    String availabilityId;
    String userId;
    String serviceId;
    Status status;
    String location;
    String firstName;
    String lastName;
    String phoneNum;
    String date;
    String time;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }
}
