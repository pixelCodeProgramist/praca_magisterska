import { Component, OnInit } from '@angular/core';
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-price-list',
  templateUrl: './price-list.component.html',
  styleUrls: ['./price-list.component.css']
})
export class PriceListComponent implements OnInit {

  background!: SafeResourceUrl;
  loading: boolean = false;
  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) { }

  ngOnInit(): void {
    this.loading = true;
    this.generalInformationService.getPhotosForSection('price-section').subscribe(
      data => {
        this.loading = false;
        for (let i = 0; i < data.length; i++) {
          let objectURL = 'data:image/png;base64,' + data[i].image;
          this.background = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);;
        }
      },error => {
        this.loading = false;
      }
    );
  }

}
