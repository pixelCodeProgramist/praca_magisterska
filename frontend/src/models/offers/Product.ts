export class Product {
  image: string;
  name: string;
  canRent: boolean;
  grade: number;
  id: string;

  constructor(id: string, image: string, name: string, canRent: boolean, grade: number) {
    this.id = id
    this.image = image;
    this.name = name;
    this.canRent = canRent;
    this.grade = grade;
  }
}

export class ClassicProduct extends Product {
  prices: PriceClassic[];

  constructor(id: string, image: string, name: string, canRent: boolean, grade: number, prices: PriceClassic[]) {
    super(id, image, name, canRent, grade);
    this.prices = prices;
  }
}

export class PriceClassic {
  bikeType: string;
  priceBikeDetails: PriceClassicDetails[];

  constructor(bikeType: string, priceBikeDetails: PriceClassicDetails[]) {
    this.bikeType = bikeType;
    this.priceBikeDetails = priceBikeDetails;
  }
}

export class PriceClassicDetails {
  price: number;
  range: string;

  constructor(price: number, range: string) {
    this.price = price;
    this.range = range;
  }
}

