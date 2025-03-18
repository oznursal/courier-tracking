package com.oznursal.courier.tracking.infra.adapters.input.rest.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorMessage {
    private String key;
    private String message;
    private List<String> detail;
    private HttpStatus status;
    private LocalDateTime occurredDateTime;
}