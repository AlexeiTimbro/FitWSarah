package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.Feedback;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface FitnessPackageRepository extends JpaRepository<FitnessPackage, Integer> {
    FitnessPackage findByFitnessPackageIdentifier_ServiceId(String serviceId);

    FitnessPackage findByFitnessPackageIdentifier_ServiceIdAndPromoOffersAndFitnessPackageIdentifier_promoId(String serviceId, String promoId);
}
