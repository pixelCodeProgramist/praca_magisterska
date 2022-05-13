export interface GeneralInfoClassicProduct {
  productName: string;
  everyBeginHourPrice: number;
  dayPrice: number;
  dayAndNightPrice: number;
  currency: string;
}

export class GeneralInfoClassicPrice {
  id!: number;
  bikeType!: string;
  everyBeginHourPrice!: number;
  dayPrice!: number;
  dayAndNightPrice!: number;
  currency!: string;


  constructor(id: number, bikeType: string, everyBeginHourPrice: number, dayPrice: number, dayAndNightPrice: number, currency: string) {
    this.id = id;
    this.bikeType = bikeType;
    this.everyBeginHourPrice = everyBeginHourPrice;
    this.dayPrice = dayPrice;
    this.dayAndNightPrice = dayAndNightPrice;
    this.currency = currency;
  }

  public static clone(classic: GeneralInfoClassicPrice) {
    return new GeneralInfoClassicPrice(classic.id, classic.bikeType, classic.everyBeginHourPrice, classic.dayPrice,  classic.dayAndNightPrice, classic.currency)
  }

  public static isEquals(c1: GeneralInfoClassicPrice, c2: GeneralInfoClassicPrice) {
    return c1.dayPrice == c2.dayPrice && c1.everyBeginHourPrice == c2.everyBeginHourPrice && c1.dayAndNightPrice == c2.dayAndNightPrice;
  }

  public static isValid(classic: GeneralInfoClassicPrice) {
    return !isNaN(Number(classic.dayPrice)) &&  !isNaN(Number(classic.dayAndNightPrice)) &&  !isNaN(Number(classic.everyBeginHourPrice));
  }
}
