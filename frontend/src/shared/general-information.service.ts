import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {Observable} from "rxjs";
import {ImageForSectionResponse} from "../models/general-information/response/ImageForSectionResponse";
import {GeneralInformationResponse} from "../models/general-information/response/GeneralInformationResponse";

@Injectable({
  providedIn: 'root'
})

export class GeneralInformationService {

  private LINKS_URL = `${GlobalService.BASE_URL}/general-information/photo`;
  private GENERAL_INFO_URL = `${GlobalService.BASE_URL}/general-information/all`;

  constructor(private http: HttpClient) { }

  getPhotosForSection(section: string): Observable<ImageForSectionResponse[]> {
    let params = '?section='+section;
    return this.http.get<ImageForSectionResponse[]>(this.LINKS_URL+params);
  }


  getGeneralInfo() {
    return this.http.get<GeneralInformationResponse>(this.GENERAL_INFO_URL);
  }
}
