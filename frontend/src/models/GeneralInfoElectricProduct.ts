export class GeneralInfoElectricProduct {
  id: number;
  time: string;
  price: number;
  currency: string;

  constructor(id: number, time: string, price: number, currency: string) {
    this.id = id;
    this.time = time;
    this.price = price;
    this.currency = currency;
  }


  public static clone(electricProduct: GeneralInfoElectricProduct) {
    return new GeneralInfoElectricProduct(electricProduct.id, electricProduct.time, electricProduct.price, electricProduct.currency)
  }

  public static isEquals(e1: GeneralInfoElectricProduct, e2: GeneralInfoElectricProduct) {
    return e1.price == e2.price;
  }

  public static isValid(electricProduct: GeneralInfoElectricProduct) {
    return !isNaN(Number(electricProduct.price));
  }
}
