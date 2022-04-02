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
    this.generalInformationService.getLinks('carousel-section').subscribe(
      links=>{
        for(let i=0; i<links.length;i++) {
          this.generalInformationService.getPhoto(links[i].url).subscribe(
            data=>{
              let image: SafeUrl = this.imageFromByteSanitizer.convertToSaveUrl(data);

              this.images.push(image);
            },error => {

            }
          )
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
