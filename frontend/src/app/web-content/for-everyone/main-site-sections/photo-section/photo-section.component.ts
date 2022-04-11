import {Component, OnInit} from '@angular/core';
import {PhotoMainSiteSecondSection} from "../../../../../models/PhotoMainSiteSecondSection";
import {Lightbox} from "ngx-lightbox";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {SafeResourceUrl, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-photo-section',
  templateUrl: './photo-section.component.html',
  styleUrls: ['./photo-section.component.css']
})
export class PhotoSectionComponent implements OnInit {
  _albums: PhotoMainSiteSecondSection[] = [];


  constructor(private _lightbox: Lightbox, private generalInformationService: GeneralInformationService, public imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }


  ngOnInit(): void {
    this.generalInformationService.getPhotosForSection('photo-section').subscribe(
      data => {
        for (let i = 0; i < data.length; i++) {
          let objectURL = 'data:image/png;base64,' + data[i]?.image;
          let image = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);

          let album: PhotoMainSiteSecondSection = new class implements PhotoMainSiteSecondSection {
            caption: string = '';
            src: SafeResourceUrl = image;
            thumb: string = '';
          }

          this._albums.push(album);

        }

      }, error => {

      }
    )
  }


  open(i: number) {
    // @ts-ignore
    this._lightbox.open(this._albums, i);
  }


}
