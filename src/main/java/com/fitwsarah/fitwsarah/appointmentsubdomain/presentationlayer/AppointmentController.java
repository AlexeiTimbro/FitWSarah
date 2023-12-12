package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

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
    public AppointmentResponseModel getAppointmentById(@PathVariable String appointmentId){
        return null;
    }

    @PostMapping()
    public AppointmentResponseModel addAppointment(@RequestBody AppointmentRequestModel appointmentRequestModel){
        return appointmentService.addAppointment(appointmentRequestModel);
    }

    @PutMapping("/{appointmentId}")
    public AppointmentResponseModel updateAppointmentById(@RequestBody AppointmentResponseModel appointmentResponseModel, @PathVariable String appointmentId){
        return null;
    }

    @DeleteMapping("/{appointmentId}")
    public void cancelAppointmentById(@PathVariable String appointmentId){

    }

    @DeleteMapping()
    public void cancelAllAppointments(){

    }
}
