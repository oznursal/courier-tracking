package com.oznursal.courier.tracking.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
}
