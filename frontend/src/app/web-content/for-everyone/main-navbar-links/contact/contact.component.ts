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
  public contactFormGroup!: FormGroup;
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
    this.contactFormGroup = new FormGroup({
      firstLastName : new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]),
      email : new FormControl('', [Validators.required, Validators.email]),
      message : new FormControl('', [Validators.required])
    });
  }



  public checkError = (controlName: string, errorName: string) => {
    return this.contactFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.contactFormGroup!.controls[controlName].errors!=null && this.contactFormGroup!.controls[controlName].touched;
  }


}
