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
    this.generalInformationService.getLinks('photo-section').subscribe(
      links=>{
        for(let i=0; i<links.length;i++) {
          this.generalInformationService.getPhoto(links[i].url).subscribe(
            data=>{

              let image: SafeUrl = this.imageFromByteSanitizer.convertToSaveUrl(data);


              let album: PhotoMainSiteSecondSection = new class implements PhotoMainSiteSecondSection {
                caption: string = '';
                src: SafeResourceUrl = image;
                thumb: string = '';
              }

              this._albums.push(album);
            },error => {

            }
          )
        }

      },error => {

      }
    )
  }


  open(i: number) {
    // @ts-ignore
    this._lightbox.open(this._albums, i);
  }


}
