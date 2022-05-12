import {Byte} from "@angular/compiler/src/util";
import {SafeResourceUrl} from "@angular/platform-browser";

export class ProductGeneralOfferResponse {
  products!: ProductGeneralOffer[];

  maxPages: number = 0;

  minimalPrice!: number;
}

export class SearchBikeResponse {
  id!: number;

  name!: string;

  image!: Byte[];

  imageSafeUrl!: SafeResourceUrl;

  type!: string;

  active!: boolean;
}

export interface ProductGeneralOffer {
  id: number;

  name: string;

  image: Byte[];

  imageSafeUrl: SafeResourceUrl;

  rating: number;
}

export class AccessoryGeneralOfferResponse {
  products!: AccessoryGeneralOffer[];

  maxPages: number = 0;
}

export interface AccessoryGeneralOffer extends ProductGeneralOffer{
  minimalPrice: number;
}
