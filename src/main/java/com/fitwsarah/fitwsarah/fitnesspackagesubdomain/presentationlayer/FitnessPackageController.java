package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fitnessPackages")
public class FitnessPackageController {

    private FitnessPackageService fitnessPackageService;

    public FitnessPackageController(FitnessPackageService fitnessPackageService) {
        this.fitnessPackageService = fitnessPackageService;
    }

    @GetMapping()
    public List<FitnessPackageResponseModel> getAllFitnessServices(@RequestParam(required = false) String serviceId, @RequestParam(required = false) String status) {
        return fitnessPackageService.getAllFitnessPackages(serviceId, status);
    }

    @GetMapping("/{serviceId}")
    public FitnessPackageResponseModel getFitnessServiceById(@PathVariable String serviceId) {
        return fitnessPackageService.getFitnessPackageByFitnessPackageId(serviceId);
    }

    @PostMapping()
    public ResponseEntity<FitnessPackageResponseModel> addFitnessService(@RequestBody FitnessPackageRequestModel fitnessPackageRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fitnessPackageService.addFitnessPackage(fitnessPackageRequestModel));
    }

    @PutMapping("/{serviceId}")
    public FitnessPackageResponseModel updateFitnessService(@RequestBody FitnessPackageRequestModel fitnessPackageRequestModel, @PathVariable String serviceId) {
        return fitnessPackageService.updateFitnessPackage(fitnessPackageRequestModel, serviceId);
    }

    @PutMapping("/{serviceId}/invisible")
    public FitnessPackageResponseModel updateFitnessServiceStatus(@PathVariable String serviceId, @RequestBody String status) {
        return fitnessPackageService.updateFitnessPackageStatus(serviceId, status);
    }


}

