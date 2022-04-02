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
  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) { }

  ngOnInit(): void {

    this.generalInformationService.getLinks('price-section').subscribe(
      links => {
        for (let i = 0; i < links.length; i++) {

          this.generalInformationService.getPhoto(links[i].url).subscribe(
            data => {
              this.background = this.imageFromByteSanitizer.convertToSaveUrl(data);
            }, error => {

            }
          )
        }

      }, error => {

      }
    );
  }

}
