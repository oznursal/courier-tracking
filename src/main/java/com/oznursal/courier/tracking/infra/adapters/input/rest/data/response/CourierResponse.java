package com.oznursal.courier.tracking.infra.adapters.input.rest.data.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
