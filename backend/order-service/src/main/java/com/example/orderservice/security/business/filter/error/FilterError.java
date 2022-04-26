package com.example.orderservice.security.business.filter.error;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterError {
    private HttpStatus status;
    private String message;
    private String debugMessage;
    private String path;

    public FilterError(HttpStatus status) {
        this();
        this.status = status;
    }
}
