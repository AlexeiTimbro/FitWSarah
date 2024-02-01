package com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.authenticationsubdomain.businesslayer.MailService;
import com.fitwsarah.fitwsarah.authenticationsubdomain.datalayer.MailStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/send")
public class MailController {


    @Autowired
    private MailService mailService;

    @PostMapping("/{email}")
    public ResponseEntity<String> sendMail(@PathVariable String email, @RequestBody MailStructure body){
        mailService.sendMail(email, body);
        return ResponseEntity.ok("Email sent successfully");
    }

}
