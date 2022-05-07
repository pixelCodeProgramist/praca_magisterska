import {Byte} from "@angular/compiler/src/util";
import {SafeResourceUrl} from "@angular/platform-browser";

export class DetailBikeInformationResponse {
  id!: number;
  name!: string;
  image: Byte[] = [];
  ratingNumber!: number;
  rating!: number;
  bikeType!: string;
  offerType!: string;
  bikeOfferType!: string;
  timePriceDtoList!: TimePriceDto[];
  guidePrice!: number;
  frames: string[] = [];
  safeUrl!: SafeResourceUrl;
}

export interface TimePriceDto {
  time: string;
  price: number;
}
