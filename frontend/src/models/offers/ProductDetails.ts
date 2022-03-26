export class Product {
  image: string;
  name: string;
  price: number;
  canRent: boolean;
  grade: number;
  id: string;

  constructor(id: string, image: string, name: string, price: number, canRent: boolean, grade: number) {
    this.id = id
    this.image = image;
    this.name = name;
    this.price = price;
    this.canRent = canRent;
    this.grade = grade;
  }
}
