export class OrderRequest {
  bikeId: number;
  selectedFrameOption: string;
  selectedHourBeginTripOption: string;
  accessoryId: number;
  price: number;


  constructor(bikeId: number, selectedFrameOption: string, selectedHourBeginTripOption: string, accessoryId: number, price: number) {
    this.bikeId = bikeId;
    this.selectedFrameOption = selectedFrameOption;
    this.selectedHourBeginTripOption = selectedHourBeginTripOption;
    if(accessoryId != undefined) this.accessoryId = accessoryId;
    else this.accessoryId = -1;
    this.price = price;
  }
}
