package com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNoteRepository;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteRequestMapper;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteResponseMapper;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachNoteServiceImpl implements CoachNoteService{

    private CoachNoteRepository coachNoteRepository;
    private CoachNoteRequestMapper coachNoteRequestMapper;
    private CoachNoteResponseMapper coachNoteResponseMapper;

    public CoachNoteServiceImpl(CoachNoteRepository coachNoteRepository, CoachNoteRequestMapper coachNoteRequestMapper, CoachNoteResponseMapper coachNoteResponseMapper){
        this.coachNoteRepository = coachNoteRepository;
        this.coachNoteRequestMapper = coachNoteRequestMapper;
        this.coachNoteResponseMapper = coachNoteResponseMapper;
    }

    @Override
    public List<CoachNoteResponseModel> getCoachNoteByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return coachNoteResponseMapper.entityListToResponseModelList(coachNoteRepository.findCoachNoteByUserId(userId));
    }

    @Override
    public List<CoachNoteResponseModel> getAllCoachNotes() {
        return coachNoteResponseMapper.entityListToResponseModelList(coachNoteRepository.findAll());
    }

}
