package com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer;



import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.Availability;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.AvailabilityRepository;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datamapperlayer.AvailabilityRequestMapper;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datamapperlayer.AvailabilityResponseMapper;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityRequestModel;
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
    private AvailabilityRequestMapper availabilityRequestMapper;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, AvailabilityResponseMapper availabilityResponseMapper, AvailabilityRequestMapper availabilityRequestMapper){
        this.availabilityResponseMapper = availabilityResponseMapper;
        this.availabilityRepository = availabilityRepository;
        this.availabilityRequestMapper = availabilityRequestMapper;
    }

    @Override
    public List<AvailabilityResponseModel> getAllAvailabilities(String dayOfWeek) {
        Set<Availability> availabilities = new HashSet<>();
        availabilities.addAll(availabilityRepository.findAllByDayOfWeek(dayOfWeek));

        return availabilityResponseMapper.entityListToResponseModelList(availabilities.stream().sorted(Comparator.comparing(availability -> availability.getAvailabilityIdentifier().getAvailabilityId())).toList());
    }

    @Override
    public AvailabilityResponseModel addAvailability(String dayOfWeek, AvailabilityRequestModel availabilityRequestModel) {
        Availability availability = availabilityRequestMapper.requestModelToEntity(availabilityRequestModel);
        availability.setDayOfWeek(dayOfWeek);
        Availability savedAvailability = availabilityRepository.save(availability);
        return availabilityResponseMapper.entityToResponseModel(savedAvailability);
    }

    @Override
    public void deleteAvailability(String availabilityId) {
        Availability existingAvailability = availabilityRepository.findAvailabilityByAvailabilityIdentifier_AvailabilityId(availabilityId);
        if(existingAvailability == null){
            return;
        }
        availabilityRepository.delete(existingAvailability);
    }
}
