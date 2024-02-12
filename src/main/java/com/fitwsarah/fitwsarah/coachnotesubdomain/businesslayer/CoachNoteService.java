package com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteRequestModel;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteResponseModel;

import java.util.List;

public interface CoachNoteService {

    List<CoachNoteResponseModel> getCoachNoteByUserId(String userId);

    List<CoachNoteResponseModel> getAllCoachNotes();

    CoachNoteResponseModel addCoachNote(CoachNoteRequestModel coachNoteRequestModel);

    CoachNoteResponseModel updateCoachNoteById(String coachNoteId, CoachNoteRequestModel coachNoteRequestModel);

    void deleteCoachNoteById(String coachNoteId);
}
