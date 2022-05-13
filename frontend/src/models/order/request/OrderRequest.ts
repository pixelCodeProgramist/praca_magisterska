export class OrderRequest {
  bikeId: number;
  selectedFrameOption: string;
  beginDateOrder: Date;
  endDateOrder: Date;
  accessoryId: number;
  price: number;
  withBikeTrip: boolean;


  constructor(bikeId: number, selectedFrameOption: string, beginDateOrder: Date, endDateOrder: Date, accessoryId: number, price: number, withBikeTrip: boolean) {
    this.bikeId = bikeId;
    this.selectedFrameOption = selectedFrameOption;
    this.beginDateOrder = beginDateOrder;
    this.endDateOrder = endDateOrder;
    this.accessoryId = accessoryId;
    this.price = price;
    this.withBikeTrip = withBikeTrip;
  }
}
