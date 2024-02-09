package com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer;



import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.Availability;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.AvailabilityRepository;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datamapperlayer.AvailabilityResponseMapper;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityResponseModel;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private AvailabilityRepository availabilityRepository;
    private AvailabilityResponseMapper availabilityResponseMapper;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, AvailabilityResponseMapper availabilityResponseMapper){
        this.availabilityResponseMapper = availabilityResponseMapper;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<AvailabilityResponseModel> getAllAppointments(String date) {
        Set<Availability> availabilities = new HashSet<>();
        availabilities.addAll(availabilityRepository.findAllByDate(date));

        return availabilityResponseMapper.entityListToResponseModelList(availabilities.stream().sorted(Comparator.comparing(availability -> availability.getAvailabilityIdentifier().getAvailabilityId())).toList());
    }
}
