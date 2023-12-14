package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fitnessPackages")
@CrossOrigin(origins = "http://localhost:3000")
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
        return null;
    }
    @PostMapping()
    public FitnessPackageResponseModel addFitnessService(@RequestBody FitnessPackageResponseModel fitnessPackageResponseModel){
        return null;
    }

    @PutMapping("/{serviceId}")
    public FitnessPackageResponseModel updateFitnessService(@RequestBody FitnessPackageResponseModel fitnessPackageResponseModel, @PathVariable String serviceId){
        return null;
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
