package com.fitwsarah.fitwsarah.profilesubdomain.datalayer;

import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datalayer.TrainerPanel;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
