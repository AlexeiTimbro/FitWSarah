package com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityTest {

    @Test
    void testAvailabilityConstructor() {
        String dayOfWeek = "Monday";
        String time = "10:00";

        Availability availability = new Availability(dayOfWeek, time);

        assertEquals(dayOfWeek, availability.getDayOfWeek());
        assertEquals(time, availability.getTime());
    }
}