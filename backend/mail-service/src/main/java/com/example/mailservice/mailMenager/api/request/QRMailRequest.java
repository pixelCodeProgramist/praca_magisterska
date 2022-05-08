package com.example.mailservice.mailMenager.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QRMailRequest {
    @NotNull
    private Long orderId;
    @NotNull
    private Date beginOrder;
    @NotNull
    private Date endOrder;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String currency;
    @NotBlank
    private String mailTo;
    @NotNull
    private byte[] image;
    @NotBlank
    private String bikeName;
    @NotBlank
    private String frameName;
    private String accessoryName;

    @NotBlank
    private String token;
}

