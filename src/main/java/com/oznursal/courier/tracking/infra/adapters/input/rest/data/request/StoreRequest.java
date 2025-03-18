package com.oznursal.courier.tracking.infra.adapters.input.rest.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequest {
    private Long id;

    @NotBlank(message = "name must not be blank")
    @Size(min = 2, max = 30, message = "name must be between 2 and 30 characters long")
    private String name;

    @DecimalMin(value = "-90", message = "latitude must be between -90 and 90 value")
    @DecimalMax(value = "90", message = "latitude must be between -90 and 90 value")
    @JsonProperty("lat")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "longitude must be between -180 and 180 value")
    @DecimalMax(value = "180.0", message = "longitude must be between -180 and 180 value")
    @JsonProperty("lng")
    private Double longitude;
}
