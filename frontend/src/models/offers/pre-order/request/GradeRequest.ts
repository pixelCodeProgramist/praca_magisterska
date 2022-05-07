export class GradeRequest {
  productId: number;
  rating: number;

  constructor(productId: number, rating: number) {
    this.productId = productId;
    this.rating = rating;
  }
}
