package com.fitwsarah.fitwsarah.availabilitiessubdomain.datamapperlayer;



import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.Availability;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer.AvailabilityRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvailabilityRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(availabilityIdentifier)", target = "availabilityIdentifier", ignore = true)
    Availability requestModelToEntity(AvailabilityRequestModel availabilityRequestModel);
}
