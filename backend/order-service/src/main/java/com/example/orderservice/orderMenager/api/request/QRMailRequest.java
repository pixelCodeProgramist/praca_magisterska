package com.example.orderservice.orderMenager.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QRMailRequest {
    private Long orderId;

    private Date beginOrder;

    private Date endOrder;

    private BigDecimal price;

    private String currency;

    private byte[] image;

    private String mailTo;

    private String bikeName;

    private String frameName;

    private String accessoryName;

    private String token;

    private String service;
}
