package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/feedbacks")
public class FeedbackController {
    //for later use

/*
4
    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }
    @GetMapping()
    public List<FeedbackResponseModel> getAllFeedbackThreads(){
        return null;
    }

    @GetMapping("/{feedbackId}")
    public FeedbackResponseModel getFeedbackById(@PathVariable String feedbackId){
        return null;
    }

    @PostMapping()
    public FeedbackResponseModel addFeedback(@RequestBody FeedbackRequestModel feedbackRequestModel){
        return null;
    }

    //We should not be able to change a feedback (Correct Elias if he is wrong)

    @DeleteMapping("/{feedbackId}")
    public void deleteFeedbackById(@PathVariable String feedbackId){

    }

    @DeleteMapping()
    public void deleteAllFeedbackThreads(){

    }

     */
}
