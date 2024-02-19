package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void appointmentConstructorTest() {
        String availabilityId = "availabilityId";
        String userId = "userId";
        String serviceId = "serviceId";
        Status status = Status.ACTIVE;
        String location = "location";
        String firstName = "firstName";
        String lastName = "lastName";
        String phoneNum = "phoneNum";
        String date = "date";
        String time = "time";

        Appointment appointment = new Appointment(availabilityId, userId, serviceId, status, location, firstName, lastName, phoneNum, date, time);

        assertEquals(availabilityId, appointment.getAvailabilityId());
        assertEquals(userId, appointment.getUserId());
        assertEquals(serviceId, appointment.getServiceId());
        assertEquals(status, appointment.getStatus());
        assertEquals(location, appointment.getLocation());
        assertEquals(firstName, appointment.getFirstName());
        assertEquals(lastName, appointment.getLastName());
        assertEquals(phoneNum, appointment.getPhoneNum());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
    }

}