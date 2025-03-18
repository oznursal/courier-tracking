package com.oznursal.courier.tracking.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
