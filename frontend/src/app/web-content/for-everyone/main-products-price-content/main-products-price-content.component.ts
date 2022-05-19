import {AfterContentChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {GeneralInfoElectricProduct} from "../../../../models/GeneralInfoElectricProduct";
import {GeneralInfoClassicProduct} from "../../../../models/GeneralInfoClassicProduct";
import {OfferService} from "../../../../shared/offer.service";


@Component({
  selector: 'app-main-products-price-content',
  templateUrl: './main-products-price-content.component.html',
  styleUrls: ['./main-products-price-content.component.css']
})
export class MainProductsPriceContentComponent implements OnInit {

  isClassicOfferButtonActive: boolean = true;
  generalInfoElectricProducts: GeneralInfoElectricProduct[] = []
  generalInfoClassicProducts: GeneralInfoClassicProduct[] = [];
  loadingClassic: boolean = false;
  loadingElectric: boolean = false;


  constructor(private offerService: OfferService) {
  }



  ngOnInit(): void {

    this.isClassicOfferButtonActive = true;
    this.loadingClassic = true;
    this.loadingElectric = true;
    this.offerService.getGeneralPriceListClassicInfo().subscribe(data => {
      this.generalInfoClassicProducts = data;
      this.loadingClassic = false;
    }, error => {
      this.loadingClassic = false;
    });

    this.offerService.getGeneralPriceListElectricInfo().subscribe(data => {
      this.generalInfoElectricProducts = data;
      this.loadingElectric = false;
    }, error => {
      this.loadingElectric = false;
    });
  }


  changeStateButton() {
    this.isClassicOfferButtonActive = !this.isClassicOfferButtonActive;
  }

  isNumber(numStr: string) {
    return !isNaN(Number(numStr));
  }

}
