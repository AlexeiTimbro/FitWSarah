package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<FeedbackResponseModel> getAllFeedbackThreads(@RequestParam(required = false) String feedbackId, @RequestParam(required = false) String userid, @RequestParam(required = false) String status){
        return feedbackService.getAllFeedback(feedbackId, userid, status);
    }



    @GetMapping("/{feedbackId}")
    public FeedbackResponseModel getFeedbackById(@PathVariable String feedbackId){
        return null;
    }
    @PostMapping()
    public ResponseEntity<FeedbackResponseModel> addFeedback(@RequestBody FeedbackRequestModel feedbackRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.addFeedback(feedbackRequestModel));
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedbackById(@PathVariable String feedbackId){
        feedbackService.removeFeedback(feedbackId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PatchMapping("/{feedbackId}/publish")
    public FeedbackResponseModel updateFeedbackState(@PathVariable String feedbackId,@RequestBody String status){
        return feedbackService.updateFeedbackState(feedbackId, status);
    }
    @DeleteMapping()
    public void deleteAllFeedbackThreads(){
    }
}
