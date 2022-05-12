import {Byte} from "@angular/compiler/src/util";
import {SafeResourceUrl} from "@angular/platform-browser";

export class DetailBikeInformationResponse {
  id!: number;
  name!: string;
  image: Byte[] = [];
  ratingNumber!: number;
  rating!: number;
  bikeType!: string;
  bikeOfferType!: string;
  timePriceDtoList!: TimePriceDto[];
  guidePrice!: number;
  frames: Frame[] = [];
  safeUrl!: SafeResourceUrl;
  imageSafeUrl!: SafeResourceUrl;
}

export interface Frame {
  frameSize: string;
  quantity: number;
}

export interface TimePriceDto {
  time: string;
  price: number;
}
