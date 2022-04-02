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
    this.generalInformationService.getLinks('time-section').subscribe(
      links=>{
        for(let i=0; i<links.length;i++) {
          this.generalInformationService.getPhoto(links[i].url).subscribe(
            data=>{
              this.image = this.imageFromByteSanitizer.convertToSaveUrl(data);

            },error => {

            }
          )
        }

      },error => {

      }
    )


  }


}
