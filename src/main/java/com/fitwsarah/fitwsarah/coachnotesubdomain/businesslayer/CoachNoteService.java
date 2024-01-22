package com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteResponseModel;

import java.util.List;

public interface CoachNoteService {

    List<CoachNoteResponseModel> getCoachNoteByUserId(String userId);
}
