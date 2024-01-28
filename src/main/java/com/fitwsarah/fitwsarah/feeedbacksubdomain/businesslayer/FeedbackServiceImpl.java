package com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.Feedback;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.FeedbackRepository;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.State;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datamapperlayer.FeedbackRequestMapper;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datamapperlayer.FeedbackResponseMapper;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackRequestModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackResponseMapper feedbackResponseMapper;

    private FeedbackRepository feedbackRepository;

    private FeedbackRequestMapper feedbackRequestMapper;


    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackRequestMapper feedbackRequestMapper, FeedbackResponseMapper feedbackResponseMapper){
        this.feedbackRepository = feedbackRepository;
        this.feedbackRequestMapper = feedbackRequestMapper;
        this.feedbackResponseMapper = feedbackResponseMapper;
    }
    @Override
    public List<FeedbackResponseModel> getAllFeedback() {
        return feedbackResponseMapper.entityListToResponseModelList(feedbackRepository.findAll());
    }

    @Override
    public FeedbackResponseModel getFeedbackByFeedbackId(String feedbackId) {
        return null;
    }

    @Override
    public FeedbackResponseModel addFeedback(FeedbackRequestModel feedbackRequestModel) {
        Feedback feedback = feedbackRequestMapper.requestModelToEntity(feedbackRequestModel);

        feedback.setStatus(State.INVISIBLE);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return feedbackResponseMapper.entityToResponseModel(savedFeedback);
    }

    @Override
    public FeedbackResponseModel updateFeedback(FeedbackResponseModel feedbackResponseModel, String feedbackId) {
        return null;
    }

    @Override
    public void removeFeedback(String feedbackId) {

    }
}
