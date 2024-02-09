package com.fitwsarah.fitwsarah.availabilitiessubdomain.datamapperlayer;


import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.Availability;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityResponseMapper {

    @Mapping(expression = "java(availability.getAvailabilityIdentifier().getAvailabilityId())", target = "availabilityId")
    AvailabilityResponseModel entityToResponseModel(Availability availability);

    List<AvailabilityResponseModel> entityListToResponseModelList(List<Availability> availabilities);
}
