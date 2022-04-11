import {Component, OnInit, ViewChild} from '@angular/core';
import {NgbCarousel} from "@ng-bootstrap/ng-bootstrap";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {SafeResourceUrl, SafeUrl} from "@angular/platform-browser";
import {PhotoMainSiteSecondSection} from "../../../../../models/PhotoMainSiteSecondSection";

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit {
  @ViewChild('carousel', {static: true})
  carousel!: NgbCarousel;
  images: SafeResourceUrl[] = []

  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }

  ngOnInit(): void {
    this.generalInformationService.getPhotosForSection('carousel-section').subscribe(
      data=>{
        for(let i=0; i<data.length;i++) {
          let objectURL = 'data:image/png;base64,' + data[i]?.image;
          let image = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
          this.images.push(image);
        }

      },error => {

      }
    )
  }


  prev() {
    this.carousel.prev();
  }

  next() {
    this.carousel.next();
  }

}
