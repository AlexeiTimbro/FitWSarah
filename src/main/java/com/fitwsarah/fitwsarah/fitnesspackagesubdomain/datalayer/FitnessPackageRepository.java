package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.Feedback;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

@Embeddable
public interface FitnessPackageRepository extends ReactiveCrudRepository<FitnessPackage, Integer> {
    Mono<FitnessPackage> findByFitnessPackageIdentifier_ServiceId(String serviceId);
    Mono<FitnessPackage> findFitnessPackageByFitnessPackageIdentifier_ServiceIdAndPromoIdentifier_PromoId(String serviceId, String promoId);
}
