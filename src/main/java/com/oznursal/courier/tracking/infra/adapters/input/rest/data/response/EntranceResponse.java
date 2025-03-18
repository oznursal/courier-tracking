package com.oznursal.courier.tracking.infra.adapters.input.rest.data.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntranceResponse {
    private Long id;
    private Double latitude;
    private Double longitude;
    private Long courierId;
    private Long storeId;
    private LocalDateTime enteredAt;
}
