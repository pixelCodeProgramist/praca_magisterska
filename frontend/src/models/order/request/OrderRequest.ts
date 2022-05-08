export class OrderRequest {
  bikeId: number;
  selectedFrameOption: string;
  beginDateOrder: Date;
  endDateOrder: Date;
  accessoryId: number;
  price: number;


  constructor(bikeId: number, selectedFrameOption: string, beginDateOrder: Date, endDateOrder: Date, accessoryId: number, price: number) {
    this.bikeId = bikeId;
    this.selectedFrameOption = selectedFrameOption;
    this.beginDateOrder = beginDateOrder;
    this.endDateOrder = endDateOrder;
    this.accessoryId = accessoryId;
    this.price = price;
  }
}
