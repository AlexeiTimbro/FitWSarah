package com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AvailabilityRequestModel {
    private LocalDateTime datetime;
    private String time;
    private String date;
}
