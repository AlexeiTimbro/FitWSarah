package com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Embeddable
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {

    List<Availability> findAllByDate(String date);
}
