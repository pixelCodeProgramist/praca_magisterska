import { Component, OnInit } from '@angular/core';
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {SafeResourceUrl} from "@angular/platform-browser";
import {
  GeneralInformationResponse
} from "../../../../../models/general-information/response/GeneralInformationResponse";

@Component({
  selector: 'app-time-section',
  templateUrl: './time-section.component.html',
  styleUrls: ['./time-section.component.css']
})
export class TimeSectionComponent implements OnInit {

  image!: SafeResourceUrl;
  generalInformation!: GeneralInformationResponse;

  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }

  ngOnInit(): void {
    this.generalInformationService.getGeneralInfo().subscribe(
      data=>{
        this.generalInformation = data;
      },error => {

      }
    )
    this.generalInformationService.getPhotosForSection('time-section').subscribe(
      data=>{
        for(let i=0; i<data.length;i++) {
          let objectURL = 'data:image/png;base64,' + data[i]?.image;
          this.image = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        }

      },error => {

      }
    )


  }


}
