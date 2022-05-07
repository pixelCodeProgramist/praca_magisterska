package com.example.orderservice.orderMenager.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateAndHourOfReservationRequest {

    @NotBlank
    private Long bikeId;

    @NotBlank
    private String frame;

    @NotBlank
    private String reservationRange;

    @Future
    private Date reservationTime;

    @NotBlank
    private String token;

    private int possibleAvailableBikeNumber;

    private Long offerId;

    private Long bikeFrameId;
}
