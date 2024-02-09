package com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNote;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNoteRepository;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteRequestMapper;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteResponseMapper;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteRequestModel;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteResponseModel;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public CoachNoteResponseModel addCoachNote(CoachNoteRequestModel coachNoteRequestModel) {
        CoachNote coachNote = coachNoteRequestMapper.requestModelToEntity(coachNoteRequestModel);
        CoachNote savedCoachNote = coachNoteRepository.save(coachNote);
        return coachNoteResponseMapper.entityToResponseModel(savedCoachNote);
    }

    @Override
    public CoachNoteResponseModel updateCoachNoteById(String coachNoteId, CoachNoteRequestModel coachNoteRequestModel) {

        CoachNote coachNote = coachNoteRepository.findCoachNoteByCoachNoteIdentifier_CoachNoteId(coachNoteId);

        if (coachNote == null) {
            throw new EntityNotFoundException("Coach Note with ID " + coachNoteId + " not found");
        }

        coachNote.setUserId(coachNoteRequestModel.getContent_EN());
        coachNote.setContent_EN(coachNoteRequestModel.getContent_FR());

        coachNoteRepository.save(coachNote);

        return coachNoteResponseMapper.entityToResponseModel(coachNote);
    }

}
