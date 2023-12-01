package com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datalayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface TrainerPanelRepository extends JpaRepository<TrainerPanel, Integer> {
}
