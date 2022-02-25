import {Component, HostListener, OnInit} from '@angular/core';
import * as L from 'leaflet';
import {FlipCard} from "../../../models/FlipCardSectionInfo";
import {MapForContact} from "../../../bisuness/MapForContact";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  flipCards!: FlipCard[];
  map1: MapForContact | undefined;
  map2: MapForContact | undefined;
  public addShopFormGroup!: FormGroup;
  constructor() {
  }

  ngOnInit(): void {
    this.flipCards = [
      new class implements FlipCard {
        faClass: string = 'fa fa-map-marker';
        cardFrontDescription: string = 'Adres';
        cardBackDescription: string = '<p>Kielecka 3, 26-010 Święta Katarzyna, Polska</p><p>Kakonin 6b, 26-004 Kakonin, Polska</p>';
      },
      new class implements FlipCard {
        faClass: string = 'fa fa-phone';
        cardFrontDescription: string = 'Telefon1';
        cardBackDescription: string = '+48 123 456 778';
      },
      new class implements FlipCard {
        faClass: string = 'fa fa-phone';
        cardFrontDescription: string = 'Telefon2';
        cardBackDescription: string = '+48 123 456 778';
      },
      new class implements FlipCard {
        faClass: string = 'fa fa-envelope';
        cardFrontDescription: string = 'Telefon2';
        cardBackDescription: string = 'nxbike@gmail.com';
      }
    ];
    this.map1 = new MapForContact([50.9018409, 20.8747351],'map1', 'Punkt 1 - Kielecka 3, 26-010 Święta Katarzyna');
    this.map2 = new MapForContact([50.8780711,20.8994074],'map2', 'Punkt 2 - Kakonin 6b, 26-004 Kakonin');
    this.map1.initMap();
    this.map2.initMap();
    this.addShopFormGroup = new FormGroup({
      shopName : new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(20)]),
      shopAddress : new FormControl('', [Validators.required, Validators.maxLength(200)])
    });
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event: any) {
    let scrollableList = Array.from(document.getElementsByClassName('scrollable') as HTMLCollectionOf<HTMLElement>);
    let value: number = -window.scrollY;
    if (value > -250 && scrollableList[0].clientWidth > 1210) {
      scrollableList.forEach((scrollable, index) => {
        if (index > 0) value *= 0.01;
        scrollable!.style.transform = 'translateY(calc(-25% - ' + value + 'px)';
      })
    }
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.addShopFormGroup.controls[controlName].hasError(errorName);
  }


}
