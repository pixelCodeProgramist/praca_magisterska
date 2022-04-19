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

@Injectable({
    providedIn: 'root'
})

export class ContactService {

    private CONTACT_URL = `${GlobalService.BASE_URL}/mail/contact`;

    constructor(private http: HttpClient) {
    }

    sendMailForContact(contact: ContactRequest) {
        return this.http.post<any>(this.CONTACT_URL,contact);
    }
}

