package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountIdentifier;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelIdentifier;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageIdentifier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class Availability {

    private String availableStatus;
    private String availableDate;

    @SuppressWarnings("unused")
    Availability() {
    }

    public Availability(@NotNull String availableStatus, @NotNull String availableDate) {
        Objects.requireNonNull(this.availableStatus = availableStatus);
        Objects.requireNonNull(this.availableDate = availableDate);
    }

    public String getAvailableStatus() {
        return availableStatus;
    }

    public String getAvailableDate() {
        return availableDate;
    }
}
