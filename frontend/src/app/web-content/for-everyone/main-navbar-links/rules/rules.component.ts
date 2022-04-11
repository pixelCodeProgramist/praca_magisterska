import {Component, OnInit} from '@angular/core';
import {SafeResourceUrl} from "@angular/platform-browser";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";

@Component({
  selector: 'app-rules',
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.css']
})
export class RulesComponent implements OnInit {

  background!: SafeResourceUrl;
  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) { }

  ngOnInit(): void {

    this.generalInformationService.getPhotosForSection('rules-section').subscribe(
      data => {
        for (let i = 0; i < data.length; i++) {
          let objectURL = 'data:image/png;base64,' + data[i].image;
          this.background = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);;
        }
      }, error => {

      }
    );

  }

}
