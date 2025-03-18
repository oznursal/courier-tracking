package com.oznursal.courier.tracking.infra.adapters.input.rest.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationRequest {
    private Long id;

    @Min(value = 1, message = "courierId must be greater than 1")
    private Long courierId;

    @DecimalMin(value = "-90", message = "latitude must be between -90 and 90 value")
    @DecimalMax(value = "90", message = "latitude must be between -90 and 90 value")
    @JsonProperty("lat")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "longitude must be between -180 and 180 value")
    @DecimalMax(value = "180.0", message = "longitude must be between -180 and 180 value")
    @JsonProperty("lng")
    private Double longitude;

    @PastOrPresent(message = "time must be past or now")
    private LocalDateTime time;
}
