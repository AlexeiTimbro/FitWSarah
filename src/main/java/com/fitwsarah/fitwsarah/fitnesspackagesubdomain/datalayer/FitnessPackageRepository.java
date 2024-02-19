package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface FitnessPackageRepository extends JpaRepository<FitnessPackage, Integer> {
    FitnessPackage findByFitnessPackageIdentifier_ServiceId(String serviceId);
}
