package com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer;


import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.Availability;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.AvailabilityRepository;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datamapperlayer.AvailabilityRequestMapper;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datamapperlayer.AvailabilityResponseMapper;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityRequestModel;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ActiveProfiles("test")
class AvailabilityServiceUnitTest {
    @InjectMocks
    AvailabilityServiceImpl availabilityService;

    @Mock
    AvailabilityRepository availabilityRepository;

    @Mock
    private AvailabilityResponseMapper availabilityResponseMapper;

    @Mock
    private AvailabilityRequestMapper availabilityRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void addAvailabilitiesByDayOfWeek_shouldSucceed(){
        String dayOfWeek = "Monday";
        AvailabilityRequestModel availabilityRequestModel = new AvailabilityRequestModel("10:00",dayOfWeek);
        Availability mockedEntity = mock(Availability.class);
        AvailabilityResponseModel mockedResponse = new AvailabilityResponseModel("uuid-1", dayOfWeek, "10:00");

        when(availabilityResponseMapper.entityToResponseModel(mockedEntity)).thenReturn(mockedResponse);
        when(availabilityRequestMapper.requestModelToEntity(availabilityRequestModel)).thenReturn(mockedEntity);
        when(availabilityRepository.save(mockedEntity)).thenReturn(mockedEntity);

        AvailabilityResponseModel result = availabilityService.addAvailability(dayOfWeek, availabilityRequestModel);

        assertNotNull(result);
        assertEquals("uuid-1", result.getAvailabilityId());
        assertEquals(availabilityRequestModel.getDayOfWeek(), result.getDayOfWeek());
        assertEquals(availabilityRequestModel.getTime(), result.getTime());
    }
    @Test
    public void removeAvailabilitiesByDayOfWeek_shouldSucceed(){
        String availabilityId = "uuid-avail1";
        Availability entity = mock(Availability.class);

        when(availabilityRepository.findAvailabilityByAvailabilityIdentifier_AvailabilityId(availabilityId)).thenReturn(entity);

        availabilityService.deleteAvailability(availabilityId);

        verify(availabilityRepository, times(1)).delete(entity);

    }
}