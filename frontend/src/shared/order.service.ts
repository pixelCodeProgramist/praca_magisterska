import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {BackendResponse} from "../models/BackendResponse";
import {OrderRequest} from "../models/order/request/OrderRequest";
import {BikeRepairRequest} from "../models/order/request/BikeRepairRequest";
import {HistoryTransactionResponse} from "../models/order/response/HistoryTransactionResponse";

@Injectable({
  providedIn: 'root'
})

export class OrderService {

  private MAKE_ORDER_URL = `${GlobalService.BASE_URL}/order/makeOrder`;
  private MAKE_ORDER_BIKE_REPAIR_URL = `${GlobalService.BASE_URL}/order/makeOrder/repairBike`;
  private HISTORY_ORDER_REPAIR_URL = `${GlobalService.BASE_URL}/order/order-history`;


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

  getOrderHistory(page: number, id?: number) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    if(id!=undefined)
      return this.http.get<HistoryTransactionResponse>(this.HISTORY_ORDER_REPAIR_URL+'/'+id+'/'+page, {headers});
    else
      return this.http.get<HistoryTransactionResponse>(this.HISTORY_ORDER_REPAIR_URL+'/'+page, {headers});
  }
}
