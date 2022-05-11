package com.example.orderservice.orderMenager.api.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderHistory {
    private String name;
    private Date beginDate;
    private Date endDate;
    private BigDecimal price;
    private String currency;
    private byte[] qrCodeImage;
}
