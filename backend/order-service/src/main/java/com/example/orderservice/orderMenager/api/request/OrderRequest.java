package com.example.orderservice.orderMenager.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequest {
    private Long bikeId;

    private Long accessoryId;

    @Size(max = 5)
    @Column(precision=10, scale=2)
    private BigDecimal price;

    @NotBlank
    private String selectedFrameOption;

    @NotBlank
    @Future
    private Date beginDateOrder;

    @NotBlank
    @Future
    private Date endDateOrder;
}
