package com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNote;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoachNoteRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(coachNoteIdentifier)", target = "coachNoteIdentifier", ignore = true)
    CoachNote requestModelToEntity(CoachNoteRequestModel coachNoteRequestModel);
}
