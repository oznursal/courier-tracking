package com.oznursal.courier.tracking.infra.adapters.input.rest.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntranceRequest {
    private Long id;

    @Min(value = 1, message = "courierId must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "courierId must be less than max long value")
    private Long courierId;

    @Min(value = 1, message = "storeId must be greater than 1")
    private Long storeId;

    @DecimalMin(value = "-90", message = "latitude must be between -90 and 90 value")
    @DecimalMax(value = "90", message = "latitude must be between -90 and 90 value")
    @JsonProperty("lat")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "longitude must be between -180 and 180 value")
    @DecimalMax(value = "180.0", message = "longitude must be between -180 and 180 value")
    @JsonProperty("lng")
    private Double longitude;

    @JsonProperty("time")
    private LocalDateTime enteredTime = LocalDateTime.now();
}
