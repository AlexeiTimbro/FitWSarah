package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/fitnessPackages")
public class FitnessPackageController {
    private FitnessPackageService fitnessPackageService;

    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FitnessPackageResponseModel> getAllFitnessServices(){
        return fitnessPackageService.getAllFitnessPackages();
    }

    @GetMapping("/{serviceId}")
    public Mono<ResponseEntity<FitnessPackageResponseModel>> getFitnessServiceById(@PathVariable String serviceId){
        return null;
    }
    @PostMapping()
    public Mono<ResponseEntity<FitnessPackageResponseModel>> addFitnessService(@RequestBody Mono<FitnessPackageResponseModel> fitnessPackageResponseModel){
        return null;
    }

    @PutMapping("/{serviceId}")
    public Mono<ResponseEntity<FitnessPackageResponseModel>> updateFitnessService(@RequestBody Mono<FitnessPackageResponseModel> fitnessPackageResponseModel, @PathVariable String serviceId){
        return null;
    }

    @DeleteMapping("/{serviceId}")
    public Mono<ResponseEntity<Void>> deleteFitnessService(@PathVariable String serviceId){
    return null;
    }

    @DeleteMapping()
    public Mono<ResponseEntity<Void>> deleteAllFitnessServices(){
    return null;
    }

    //All requests regarding promo offers in a fitness service
    @GetMapping("/{serviceId}/promoOffers/{promoId}")
    public Mono<ResponseEntity<FitnessPackageResponseModel>> getPromoOfferByFitnessServiceId(@PathVariable String serviceId, @PathVariable String promoId){
        return null;
    }
    @PostMapping("/{serviceId}")
    public Mono<ResponseEntity<FitnessPackageResponseModel>> addPromoOfferByFitnessServiceId(@RequestBody PromoOfferRequestModel promoOfferRequestModel,@PathVariable String serviceId){
        return null;
    }
    @PutMapping("/{serviceId}/promoOffers/{promoId}")
    public Mono<ResponseEntity<FitnessPackageResponseModel>> updatePromoOfferByFitnessServiceId(@RequestBody PromoOfferRequestModel promoOfferRequestModel, @PathVariable String serviceId, @PathVariable String promoId){
        return null;
    }
    @DeleteMapping("/{serviceId}/promoOffers/{promoId}")
    public Mono<ResponseEntity<Void>> deletePromoOfferByFitnessServiceId(@PathVariable String serviceId, @PathVariable String promoId){
    return null;
    }
}
