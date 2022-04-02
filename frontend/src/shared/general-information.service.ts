import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {Observable} from "rxjs";
import {ImageForSectionModel} from "../models/general-information/response/ImageForSectionModel";
import {ImagesForSectionResponse} from "../models/general-information/response/ImagesForSectionResponse";
import {GeneralInformationResponse} from "../models/general-information/response/GeneralInformationResponse";

@Injectable({
  providedIn: 'root'
})

export class GeneralInformationService {

  private LINKS_URL = `${GlobalService.BASE_URL}/general-information/photo`;
  private PHOTOS_URL = `${GlobalService.BASE_URL}/general-information/photo`;
  private GENERAL_INFO_URL = `${GlobalService.BASE_URL}/general-information/all`;

  constructor(private http: HttpClient) { }

  getLinks(section: string): Observable<ImageForSectionModel[]> {

    let params = '?section='+section;
    return this.http.get<ImageForSectionModel[]>(this.LINKS_URL+params);
  }

  getPhoto(link: any):Observable<Blob> {
    const requestOptions: Object = {
      responseType: 'Blob' as 'json'
    }

    return this.http.get<Blob>(`${GlobalService.BASE_URL}/`+link,requestOptions);
  }

  getPhotos(imageForSectionModels: ImageForSectionModel[]) {
    return this.http.post<ImagesForSectionResponse[]>(this.PHOTOS_URL, imageForSectionModels);
  }

  getGeneralInfo() {
    return this.http.get<GeneralInformationResponse>(this.GENERAL_INFO_URL);
  }
}
