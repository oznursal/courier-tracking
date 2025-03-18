package com.oznursal.courier.tracking.infra.adapters.input.rest.data.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocationResponse {
    private Long id;
    private Double latitude;
    private Double longitude;
    private Long courierId;
}
