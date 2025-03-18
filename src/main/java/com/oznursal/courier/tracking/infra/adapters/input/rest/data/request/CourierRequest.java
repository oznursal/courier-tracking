package com.oznursal.courier.tracking.infra.adapters.input.rest.data.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierRequest {
    private Long id;

    @NotBlank(message = "firstName must not be blank")
    @Size(min = 2, max = 32, message = "firstName must be between 2 and 32 characters long")
    private String firstName;

    @NotBlank(message = "lastName must not be blank")
    @Size(min = 2, max = 32, message = "lastName must be between 2 and 32 characters long")
    private String lastName;

    @Email(message = "email must be valid")
    @NotBlank(message = "email must not be blank")
    private String email;

    @NotBlank(message = "phone must not be blank")
    @Size(min = 9, max = 11, message = "phone must be between 9 and 11 characters long")
    private String phone;
}
