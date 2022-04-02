import {ClassicProduct, PriceClassic} from "./Product";

export class ProductDetails extends ClassicProduct{
  gradesNumber: number;
  canGrade: boolean;
  availableFrameSize: string[];

  constructor(id: string, image: string, name: string, canRent: boolean, grade: number, prices: PriceClassic[], gradesNumber: number, canGrade: boolean, availableFrameSize: string[]) {
    super(id, image, name, canRent, grade, prices);
    this.gradesNumber = gradesNumber;
    this.canGrade = canGrade;
    this.availableFrameSize = availableFrameSize;
  }
}

export class ProductTimeDetails {
  hours!: string[];
  productDetails!: ProductDetails;

  constructor(hours: string[], productDetails: ProductDetails) {
    this.hours = hours;
    this.productDetails = productDetails;
  }
}
