import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {GeneralInfoClassicPrice, GeneralInfoClassicProduct} from "../models/GeneralInfoClassicProduct";
import {GeneralInfoElectricProduct} from "../models/GeneralInfoElectricProduct";
import {
  AccessoryGeneralOfferResponse,
  ProductGeneralOfferResponse,
  SearchBikeResponse
} from "../models/offers/ProductGeneralOfferResponse";
import {DetailBikeInformationResponse} from "../models/detail-information/response/DetailBikeInformationResponse";
import {DateAndHourOfReservationRequest} from "../models/offers/pre-order/request/DateAndHourOfReservationRequest";
import {AvailableHoursResponse} from "../models/offers/pre-order/response/AvailableHoursResponse";
import {AccessoryInOrder} from "../models/offers/pre-order/response/AccessoryInOrder";
import {GradeRequest} from "../models/offers/pre-order/request/GradeRequest";
import {BackendResponse} from "../models/BackendResponse";
import {AddOfferRequest} from "../models/offers/request/AddOfferRequest";

@Injectable({
  providedIn: 'root'
})

export class OfferService {

  private GENERAL_PRICE_LIST_INFORMATION_CLASSIC_PRODUCTS_URL = `${GlobalService.BASE_URL}/offer/general-information/classic`;
  private GENERAL_PRICE_LIST_INFORMATION_ELECTRIC_PRODUCTS_URL = `${GlobalService.BASE_URL}/offer/general-information/electric`;
  private GENERAL_OFFER_INFORMATION_CLASSIC_PRODUCTS_URL = `${GlobalService.BASE_URL}/offer/general-offer`;
  private GENERAL_SEARCH_BIKES_URL = `${GlobalService.BASE_URL}/offer/bikes`;
  private DETAILS_BIKE_INFORMATION_URL = `${GlobalService.BASE_URL}/offer/detail-information/product`;
  private AVAILABLE_HOURS_BIKE_URL = `${GlobalService.BASE_URL}/offer/available-hours`;
  private ACCESSORY_DETAIL_OFFER_URL = `${GlobalService.BASE_URL}/offer/detail-information/accessories/all`;
  private CAN_GRADE_OFFER_URL = `${GlobalService.BASE_URL}/offer/grade/canGrade`;
  private GRADE_OFFER_URL = `${GlobalService.BASE_URL}/offer/grade`;
  private ADD_OFFER_URL = `${GlobalService.BASE_URL}/offer/add`;
  private CHANGE_OFFER_ACTIVITY_URL = `${GlobalService.BASE_URL}/offer/activity`;
  private GENERAL_PRICE_LIST_CLASSIC_BIKES_URL = `${GlobalService.BASE_URL}/offer/general-information/classic/prices`;
  private UPDATE_CLASSIC_PRICES_URL = `${GlobalService.BASE_URL}/offer/classicPrices`;
  private UPDATE_ELECTRIC_PRICES_URL = `${GlobalService.BASE_URL}/offer/electricPrices`;

  constructor(private http: HttpClient) {
  }


  getGeneralPriceListClassicInfo() {
    return this.http.get<GeneralInfoClassicProduct[]>(this.GENERAL_PRICE_LIST_INFORMATION_CLASSIC_PRODUCTS_URL);
  }

  getGeneralPriceListClassicBikes() {
    return this.http.get<GeneralInfoClassicPrice[]>(this.GENERAL_PRICE_LIST_CLASSIC_BIKES_URL);
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

  getDetailBikeInformation(id: string, all: boolean = false) {
    return this.http.get<DetailBikeInformationResponse>(this.DETAILS_BIKE_INFORMATION_URL + '/' + id + '/' + all);
  }

  getSearchBikes(all: boolean = false) {
    return this.http.get<SearchBikeResponse[]>(this.GENERAL_SEARCH_BIKES_URL  + '/' + all);
  }

  getAvailableHours(dateAndHourOfReservationRequest: DateAndHourOfReservationRequest) {
    return this.http.post<AvailableHoursResponse>(this.AVAILABLE_HOURS_BIKE_URL, dateAndHourOfReservationRequest);
  }

  getAccessoryDetailOfferResponse(range: number) {
    return this.http.get<AccessoryInOrder[]>(this.ACCESSORY_DETAIL_OFFER_URL+'/'+range);
  }

  canGrade(productId: number) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.get<boolean>(this.CAN_GRADE_OFFER_URL+'/'+productId,{headers});
  }

  grade(gradeRequest: GradeRequest) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.post<BackendResponse>(this.GRADE_OFFER_URL, gradeRequest, {headers});
  }

  addBike(addOfferRequest: AddOfferRequest) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.post<BackendResponse>(this.ADD_OFFER_URL, addOfferRequest, {headers});
  }

  changeOfferActivity(id: number) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.get<BackendResponse>(this.CHANGE_OFFER_ACTIVITY_URL+'/'+id, {headers});
  }

  modifyClassicPrices(classicPrices: GeneralInfoClassicPrice[]) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.put<BackendResponse>(this.UPDATE_CLASSIC_PRICES_URL, classicPrices,{headers});
  }

  modifyElectricPrices(electricPrices: GeneralInfoElectricProduct[]) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.put<BackendResponse>(this.UPDATE_ELECTRIC_PRICES_URL, electricPrices,{headers});
  }
}
