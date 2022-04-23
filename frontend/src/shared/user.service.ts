import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {GeneralInfoClassicProduct} from "../models/GeneralInfoClassicProduct";
import {GeneralInfoElectricProduct} from "../models/GeneralInfoElectricProduct";
import {
  AccessoryGeneralOfferResponse,
  ProductGeneralOfferResponse,
  SearchBikeResponse
} from "../models/offers/ProductGeneralOfferResponse";
import {DetailBikeInformationResponse} from "../models/detail-information/response/DetailBikeInformationResponse";
import {ContactRequest} from "../models/contact/ContactRequest";
import {RegisterModel} from "../models/user/RegisterModel";
import {StringMessageModelResponse} from "../models/StringMessageModelResponse";

@Injectable({
    providedIn: 'root'
})

export class UserService {

    private REGISTER_URL = `${GlobalService.BASE_URL}/user/register`;

    constructor(private http: HttpClient) {
    }

    register(registerModel: RegisterModel) {
        return this.http.post<StringMessageModelResponse>(this.REGISTER_URL, registerModel);
    }
}

