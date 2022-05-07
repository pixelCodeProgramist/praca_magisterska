export class DateAndHourOfReservationRequest {
  reservationTime: Date = new Date();
  reservationRange: string = '';
  frame: string = '';
  bikeId: number = 0;
}
