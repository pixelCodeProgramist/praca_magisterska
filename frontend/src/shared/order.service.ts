import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {GeneralInfoClassicProduct} from "../models/GeneralInfoClassicProduct";
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
import {OrderRequest} from "../models/order/request/OrderRequest";
import {BikeRepairRequest} from "../models/order/request/BikeRepairRequest";

@Injectable({
  providedIn: 'root'
})

export class OrderService {

  private MAKE_ORDER_URL = `${GlobalService.BASE_URL}/order/makeOrder`;
  private MAKE_ORDER_BIKE_REPAIR_URL = `${GlobalService.BASE_URL}/order/makeOrder/repairBike`;


  constructor(private http: HttpClient) {
  }


  makeOrder(orderRequest: OrderRequest) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.post<BackendResponse>(this.MAKE_ORDER_URL, orderRequest, {headers});
  }

  makeOrderBikeRepair(orderRequest: BikeRepairRequest) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.post<BackendResponse>(this.MAKE_ORDER_BIKE_REPAIR_URL, orderRequest, {headers});
  }
}
