package com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class AvailabilityRepositoryTest {
    @Autowired
    AvailabilityRepository availabilityRepository;

    private String savedAvailabilityId;
    private String savedDayOfWeek;
    Availability savedAvailability;


    @BeforeEach
    public void setUp() {
        Availability availability = new Availability();
        AvailabilityIdentifier identifier = new AvailabilityIdentifier();
        identifier.setAvailabilityId(savedAvailabilityId);
        availability.setDayOfWeek("Monday");
        availability.setTime("10:00");
        savedAvailability = availabilityRepository.save(availability);
        savedAvailabilityId = savedAvailability.getAvailabilityIdentifier().getAvailabilityId();
        savedDayOfWeek = savedAvailability.getDayOfWeek();
    }

    @AfterEach
    public void tearDown() {
        availabilityRepository.deleteAll();
    }

    @Test
    void findAvailabilityByAvailabilityIdentifier_AvailabilityId_shouldSucceed() {
        String mockavailabilityId = "uuid-avail3232";
        Availability mockAvailability = new Availability();
        AvailabilityIdentifier identifier = new AvailabilityIdentifier();
        identifier.setAvailabilityId(mockavailabilityId);
        mockAvailability.setAvailabilityIdentifier(identifier);
        availabilityRepository.save(mockAvailability);

        // Act
        Availability found = availabilityRepository.findAvailabilityByAvailabilityIdentifier_AvailabilityId(mockavailabilityId);

        // Assert
        assertNotNull(found);
        assertEquals(mockavailabilityId, found.getAvailabilityIdentifier().getAvailabilityId());
    }
    @Test
    void findAllByDayOfWeek_shouldReturnListOfAvailabilities() {
        List<Availability> result = availabilityRepository.findAllByDayOfWeek(savedDayOfWeek);

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(feedback -> feedback.getDayOfWeek().equals(savedDayOfWeek)));
    }
}