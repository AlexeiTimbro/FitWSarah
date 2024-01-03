package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }
    @GetMapping()
    public List<AppointmentResponseModel> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{appointmentId}")
    public AppointmentResponseModel getAppointmentById(@PathVariable String appointmentId ){
     return appointmentService.getAppointmentByAppointmentId(appointmentId);

    }

    @GetMapping("/account/{accountId}")
    public List<AppointmentResponseModel> getAllAppointmentsByAccountId(@PathVariable String accountId){
        return appointmentService.getAllAppointmentsByAccountId(accountId);
    }



    @PostMapping()
    public AppointmentResponseModel addAppointmentById(@RequestBody AppointmentResponseModel appointmentResponseModel){
        return null;
    }

    @PutMapping("/{appointmentId}")
    public AppointmentResponseModel updateAppointmentById(@RequestBody AppointmentResponseModel appointmentResponseModel, @PathVariable String appointmentId){
        return null;
    }

    @PutMapping("/{appointmentId}/status")
    public AppointmentResponseModel updateAppointmentStatus(@PathVariable String appointmentId, @RequestBody String status){
        return appointmentService.updateAppointmentStatus(appointmentId, status);
    }

    @DeleteMapping("/{appointmentId}")
    public void cancelAppointmentById(@PathVariable String appointmentId){

    }

    @DeleteMapping()
    public void cancelAllAppointments(){

    }
}
