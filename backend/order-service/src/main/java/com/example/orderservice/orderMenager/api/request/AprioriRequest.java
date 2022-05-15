package com.example.orderservice.orderMenager.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AprioriRequest {

    @Past
    private Date from;

    @NotNull
    private Date to;

    @DecimalMin(value = "0.01")
    @DecimalMax(value = "1.00")
    private double support;

    @DecimalMin(value = "0.01")
    @DecimalMax(value = "1.00")
    private double confidence;
}
