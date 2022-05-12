import {Byte} from "@angular/compiler/src/util";

export class AddOfferRequest {
  name!: string;
  price!: string;
  frames: Frame[] = [];
  url: string | ArrayBuffer | null = '';
  image: Byte[] = [];
  selectedProductTypeOption!: string;
  selectedBikeOrAccessoryTypeOption!: string;
}

export class Frame {
  frameSize: string;
  quantity: number;

  constructor(frameSize: string, quantity: number) {
    this.frameSize = frameSize;
    this.quantity = quantity;
  }
}
