package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }
    @GetMapping()
    public List<FeedbackResponseModel> getAllFeedbackThreads(){
        return feedbackService.getAllFeedback();
    }
    @GetMapping("/{feedbackId}")
    public FeedbackResponseModel getFeedbackById(@PathVariable String feedbackId){
        return null;
    }
    @PostMapping()
    public ResponseEntity<FeedbackResponseModel> addFeedback(@RequestBody FeedbackRequestModel feedbackRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.addFeedback(feedbackRequestModel));
    }
    //We should not be able to change a feedback (Correct Elias if he is wrong)
    @DeleteMapping("/{feedbackId}")
    public void deleteFeedbackById(@PathVariable String feedbackId){
    }
    @DeleteMapping()
    public void deleteAllFeedbackThreads(){
    }
}
