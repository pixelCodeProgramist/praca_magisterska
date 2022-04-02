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
    this.generalInformationService.getLinks('rules-section').subscribe(
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
