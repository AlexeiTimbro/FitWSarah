package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
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

    public FitnessPackageController(FitnessPackageService fitnessPackageService){
        this.fitnessPackageService = fitnessPackageService;
    }
    @GetMapping()
    public List<FitnessPackageResponseModel> getAllFitnessServices(){
        return fitnessPackageService.getAllFitnessPackages();
    }

    @GetMapping("/{serviceId}")
    public FitnessPackageResponseModel getFitnessServiceById(@PathVariable String serviceId){
        return fitnessPackageService.getFitnessPackageByFitnessPackageId(serviceId);
    }

    @PostMapping()
    public ResponseEntity<FitnessPackageResponseModel> addFitnessService(@RequestBody FitnessPackageRequestModel fitnessPackageRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(fitnessPackageService.addFitnessPackage(fitnessPackageRequestModel));
    }

    @PutMapping("/{serviceId}")
    public FitnessPackageResponseModel updateFitnessService(@RequestBody FitnessPackageRequestModel fitnessPackageRequestModel, @PathVariable String serviceId){
        return fitnessPackageService.updateFitnessPackage(fitnessPackageRequestModel, serviceId);
    }


    @PutMapping("/{serviceId}/invisible")
    public FitnessPackageResponseModel updateFitnessPackageStatus(@PathVariable String serviceId, @RequestBody String status){
        return fitnessPackageService.updateFitnessPackageStatus(serviceId, status);
    }


    @DeleteMapping("/{serviceId}")
    public void deleteFitnessService(@PathVariable String serviceId){

    }

    @DeleteMapping()
    public void deleteAllFitnessServices(){

    }

    //All requests regarding promo offers in a fitness service
    @GetMapping("/{serviceId}/promoOffers/{promoId}")
    public PromoOfferResponseModel getPromoOfferByFitnessServiceId(@PathVariable String serviceId, @PathVariable String promoId){
        return null;
    }
    @PostMapping("/{serviceId}")
    public PromoOfferResponseModel addPromoOfferByFitnessServiceId(@RequestBody PromoOfferRequestModel promoOfferRequestModel,@PathVariable String serviceId){
        return null;
    }
    @PutMapping("/{serviceId}/promoOffers/{promoId}")
    public PromoOfferResponseModel updatePromoOfferByFitnessServiceId(@RequestBody PromoOfferRequestModel promoOfferRequestModel, @PathVariable String serviceId, @PathVariable String promoId){
        return null;
    }
    @DeleteMapping("/{serviceId}/promoOffers/{promoId}")
    public void deletePromoOfferByFitnessServiceId(@PathVariable String serviceId, @PathVariable String promoId){

    }
}
