package com.fitwsarah.fitwsarah.profilesubdomain.presentationlayer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {
    @GetMapping({"/profileId"})
    public ProfileResponseModel getProfileByProfileId() {
        return null;
    }

}
