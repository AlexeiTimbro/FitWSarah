package com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer.CoachNoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/coachnotes")
public class CoachNoteController {

    private final CoachNoteService coachNoteService;
    public CoachNoteController(CoachNoteService coachNoteService){
        this.coachNoteService = coachNoteService;
    }

    @GetMapping("/users/{userId}")
    public List<CoachNoteResponseModel> getCoachNoteByUserId(@PathVariable String userId){
        return coachNoteService.getCoachNoteByUserId(userId);
    }

    @GetMapping()
    public List<CoachNoteResponseModel> getAllCoachNotes(){
        return coachNoteService.getAllCoachNotes();
    }

    @PostMapping()
    public CoachNoteResponseModel addCoachNote(@RequestBody CoachNoteRequestModel coachNoteRequestModel){
        return coachNoteService.addCoachNote(coachNoteRequestModel);
    }
}
