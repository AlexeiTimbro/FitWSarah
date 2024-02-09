package com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AvailabilityResponseModel {
    private String availabilityId;
    private LocalDateTime datetime;
    private Boolean available;
    private String date;
    private String time;
}
