package com.example.orderservice.orderMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long orderId;

    @NotBlank
    private Date beginOrder;

    @NotBlank
    private Date endOrder;

    @NotBlank
    private String transactionToken;

    @Column(nullable = false)
    private Boolean paid;

    @Future
    private Date timeToPaid;

}
