package com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer;


import com.fitwsarah.fitwsarah.adminpanelsubdomain.businesslayer.AdminPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adminPanel")
public class AdminPanelController {
    // This is for later use

/*
    private AdminPanelService adminPanelService;

    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AdminPanelResponseModel> getAllAdmins(){
        return null;
    }
    @GetMapping("/{adminId}")
    public Mono<AdminPanelResponseModel> getAdminById(@PathVariable String adminId){
        return null;
    }
    @PostMapping()
    public Mono<AdminPanelResponseModel> addAdmin(@RequestBody Mono<AdminPanelRequestModel> adminPanelRequestModel){
        return null;
    }
    @PutMapping("/{adminId}")
    public Mono<AdminPanelResponseModel> updateAdminById(@RequestBody Mono<AdminPanelRequestModel> adminPanelRequestModel, @PathVariable String adminId){
        return null;
    }
    @DeleteMapping("/{adminId}")
    public Mono<Void> deleteAdminById(@PathVariable String adminId){
    }
 */
}
