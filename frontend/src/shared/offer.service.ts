import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {GeneralInfoClassicProduct} from "../models/GeneralInfoClassicProduct";
import {GeneralInfoElectricProduct} from "../models/GeneralInfoElectricProduct";
import {AccessoryGeneralOfferResponse, ProductGeneralOfferResponse} from "../models/offers/ProductGeneralOfferResponse";

@Injectable({
  providedIn: 'root'
})

export class OfferService {

  private GENERAL_PRICE_LIST_INFORMATION_CLASSIC_PRODUCTS_URL = `${GlobalService.BASE_URL}/offer/general-information/classic`;
  private GENERAL_PRICE_LIST_INFORMATION_ELECTRIC_PRODUCTS_URL = `${GlobalService.BASE_URL}/offer/general-information/electric`;
  private GENERAL_OFFER_INFORMATION_CLASSIC_PRODUCTS_URL = `${GlobalService.BASE_URL}/offer/general-offer`;

  constructor(private http: HttpClient) {
  }


  getGeneralPriceListClassicInfo() {
    return this.http.get<GeneralInfoClassicProduct[]>(this.GENERAL_PRICE_LIST_INFORMATION_CLASSIC_PRODUCTS_URL);
  }

  getGeneralPriceListElectricInfo() {
    return this.http.get<GeneralInfoElectricProduct[]>(this.GENERAL_PRICE_LIST_INFORMATION_ELECTRIC_PRODUCTS_URL);
  }

  getBikeGeneralOfferInformation(section: string, page: number, bikeType: string) {

    if (bikeType == 'classic') return this.http.get<ProductGeneralOfferResponse>(this.GENERAL_OFFER_INFORMATION_CLASSIC_PRODUCTS_URL + '/' + bikeType + '/' + section + '/' + page);
    if (bikeType == 'electric') return this.http.get<ProductGeneralOfferResponse>(this.GENERAL_OFFER_INFORMATION_CLASSIC_PRODUCTS_URL + '/' + bikeType + '/' + page);


    return null;
  }

  getAccessoryGeneralOfferResponse(section: string, page: number) {
    return this.http.get<AccessoryGeneralOfferResponse>(this.GENERAL_OFFER_INFORMATION_CLASSIC_PRODUCTS_URL + '/' + section + '/' + page);
  }
}
