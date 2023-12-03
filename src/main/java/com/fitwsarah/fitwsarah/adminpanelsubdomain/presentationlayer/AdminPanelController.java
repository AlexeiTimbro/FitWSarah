package com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer;


import com.fitwsarah.fitwsarah.adminpanelsubdomain.businesslayer.AdminPanelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/adminPanel")
public class AdminPanelController {
    // This is for later use

/*
    private AdminPanelService adminPanelService;
    public AdminPanelController(AdminPanelService adminPanelService){
        this.adminPanelService = adminPanelService;
    }
    @GetMapping()
    public List<AdminPanelResponseModel> getAllAdmins(){
        return null;
    }
    @GetMapping("/{adminId}")
    public AdminPanelResponseModel getAdminById(@PathVariable String adminId){
        return null;
    }
    @PostMapping()
    public AdminPanelResponseModel addAdmin(@RequestBody AdminPanelRequestModel adminPanelRequestModel){
        return null;
    }
    @PutMapping("/{adminId}")
    public AdminPanelResponseModel updateAdmin(@RequestBody AdminPanelRequestModel adminPanelRequestModel, @PathVariable String adminId){
        return null;
    }
    @DeleteMapping("/{adminId}")
    public void deleteAdminById(@PathVariable String adminId){
    }
 */
}
