package com.karol.offerservice.offerMenager.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateAndHourOfReservationWithTokenRequest extends DateAndHourOfReservationRequest{
   private String token;

   private int possibleAvailableBikeNumber;

   private Long offerId;

   private Long bikeFrameId;

   public DateAndHourOfReservationWithTokenRequest(DateAndHourOfReservationRequest d, int possibleAvailableBikeNumber,Long offerId, Long bikeFrameId,
                                                   String token) {
      super(d.getBikeId(), d.getFrame(), d.getReservationRange(), d.getReservationTime());
      this.token = token;
      this.possibleAvailableBikeNumber = possibleAvailableBikeNumber;
      this.offerId = offerId;
      this.bikeFrameId = bikeFrameId;
   }
}
