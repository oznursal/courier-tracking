package com.oznursal.courier.tracking.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocation {
    private Long id;
    private Courier courier;
    private Double latitude;
    private Double longitude;
    private LocalDateTime time;
}
