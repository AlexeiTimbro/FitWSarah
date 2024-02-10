package com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNote;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoachNoteResponseMapper {

    @Mapping(expression = "java(coachNote.getCoachNoteIdentifier().getCoachNoteId())", target = "coachNoteId")
    CoachNoteResponseModel entityToResponseModel(CoachNote coachNote);

    List<CoachNoteResponseModel> entityListToResponseModelList(List<CoachNote> coachNotes);
}
