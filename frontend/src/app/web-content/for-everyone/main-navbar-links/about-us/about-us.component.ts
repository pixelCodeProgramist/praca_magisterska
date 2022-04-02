import { Component, OnInit } from '@angular/core';
import {SafeResourceUrl} from "@angular/platform-browser";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css']
})
export class AboutUsComponent implements OnInit {
  sectionUrls: Map<string, SafeResourceUrl[]> = new Map<string, SafeResourceUrl[]>();

  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }

  ngOnInit(): void {
    this.generalInformationService.getLinks('about-section').subscribe(
      links => {
        for (let i = 0; i < links.length; i++) {
          let safeUrls: SafeResourceUrl[] = [];
          this.generalInformationService.getPhoto(links[i].url).subscribe(
            data => {
              safeUrls.push(this.imageFromByteSanitizer.convertToSaveUrl(data));
             this.sectionUrls.set(links[i].fileName, safeUrls);

            }, error => {

            }
          )
        }

      }, error => {

      }
    );

  }
}
