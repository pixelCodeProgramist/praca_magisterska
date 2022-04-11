import {Component, OnInit} from '@angular/core';
import {FlipCard, FlipCardSectionInfo} from "../../../../../models/FlipCardSectionInfo";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {SafeResourceUrl, SafeUrl} from "@angular/platform-browser";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";

@Component({
  selector: 'app-flip-card-section',
  templateUrl: './flip-card-section.component.html',
  styleUrls: ['./flip-card-section.component.css']
})
export class FlipCardSectionComponent implements OnInit {
  flipCardSectionInfo: FlipCardSectionInfo | undefined;



  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }

  ngOnInit(): void {
    this.generalInformationService.getPhotosForSection('flip-card-section').subscribe(
      data=>{
        let objectURL = 'data:image/png;base64,' + data[0]?.image;
        let image = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        this.fillFlipCardSectionInfo(image);
      },error => {


      }
    )


  }

  fillFlipCardSectionInfo(image: SafeUrl) {
    this.flipCardSectionInfo = new class implements FlipCardSectionInfo {
      background: SafeResourceUrl = image;
      title: string = 'Wypożyczalnia rowerów NxBike';
      flipCards: FlipCard[] = [
        new class implements FlipCard {
          cardFrontDescription: string = 'Wypożycz rower';
          faClass: string = 'fa fa-bicycle';
          cardBackDescription: string = 'Wybierasz się w góry? Wypożycz rower przed wejściem do oddziału w Górach Świętokrzyskich';
        },
        new class implements FlipCard {
          cardFrontDescription: string = 'Zaparkuj przy oddziale w Górach Świętokrzyskich';
          faClass: string = 'fa fa-stop-circle';
          cardBackDescription: string = 'Zaparkuj rower (posiadamy stojaki dla naszych klientów)';
        },
        new class implements FlipCard {
          cardFrontDescription: string = 'Idź w góry';
          faClass: string = 'fa fa-arrow-up';
          cardBackDescription: string = 'Zaoszczędź czas przy przeprawie rowerem. Dotrzyj na szczyt znacznie szybciej';
        },
        new class implements FlipCard {
          cardFrontDescription: string = 'Zjedź na dół';
          faClass: string = 'fa fa-arrow-down';
          cardBackDescription: string = 'Szybszy powrót do domu? Z nami to możliwe zjedź na dół zaparkowanym wcześniej rowerem';
        }
      ];

    }
  }

}
