package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<AppointmentResponseModel> getAllAppointments(@RequestParam(required = false) String appointmentid, @RequestParam(required = false) String accountid, @RequestParam(required = false) String status){
        return appointmentService.getAllAppointments(appointmentid, accountid, status);
    }

    @GetMapping("/{appointmentId}")
    public AppointmentResponseModel getAppointmentById(@PathVariable String appointmentId ){
     return appointmentService.getAppointmentByAppointmentId(appointmentId);

    }

    @GetMapping("/accounts/users/{userId}")
    public List<AppointmentResponseModel> getAllAppointmentsByUserId(@PathVariable String userId){
        return appointmentService.getAllAppointmentsByUserId(userId);
    }






    @PostMapping()
    public ResponseEntity<AppointmentResponseModel> addAppointment(@RequestBody AppointmentRequestModel appointmentRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.addAppointment(appointmentRequestModel));
    }

    @PutMapping("/{appointmentId}")
    public AppointmentResponseModel updateAppointmentById(@RequestBody AppointmentRequestModel appointmentRequestModel, @PathVariable String appointmentId){
        return appointmentService.updateAppointmentDetails(appointmentRequestModel, appointmentId);
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
