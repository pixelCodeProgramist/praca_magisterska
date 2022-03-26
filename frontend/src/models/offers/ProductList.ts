export class Product {
  image: string;
  name: string;
  price: number;
  canRent: boolean;
  grade: boolean;

  constructor(image: string, name: string, price: number, canRent: boolean, grade: boolean) {
    this.image = image;
    this.name = name;
    this.price = price;
    this.canRent = canRent;
    this.grade = grade;
  }
}
