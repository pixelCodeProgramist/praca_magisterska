import {ClassicProduct, Product} from "./Product";

export class ProductList {
  products: ClassicProduct[];
  maxPageNumber: number;

  constructor(products: ClassicProduct[], maxPageNumber: number) {
    this.products = products;
    this.maxPageNumber = maxPageNumber;
  }
}
