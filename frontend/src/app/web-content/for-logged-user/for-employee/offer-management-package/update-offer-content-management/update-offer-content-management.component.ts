import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../../../../shared/offer.service";
import {GeneralInfoClassicPrice} from "../../../../../../models/GeneralInfoClassicProduct";
import {GeneralInfoElectricProduct} from "../../../../../../models/GeneralInfoElectricProduct";

@Component({
  selector: 'app-update-offer-content-management',
  templateUrl: './update-offer-content-management.component.html',
  styleUrls: ['./update-offer-content-management.component.css']
})
export class UpdateOfferContentManagementComponent implements OnInit {

  classicPrices: GeneralInfoClassicPrice[] = [];
  classicPricesCopy: GeneralInfoClassicPrice[] = [];
  electricPrices: GeneralInfoElectricProduct[] = [];
  electricPricesCopy: GeneralInfoElectricProduct[] = [];


  constructor(private offerService: OfferService) { }

  ngOnInit(): void {

    this.offerService.getGeneralPriceListClassicBikes().subscribe(
      data=>{
        this.classicPrices = data;
        this.classicPrices.forEach(classic=>{
          this.classicPricesCopy.push(GeneralInfoClassicPrice.clone(classic));
        });

      },error => {

      }
    )

    this.offerService.getGeneralPriceListElectricInfo().subscribe(
      data=>{
        this.electricPrices = data;
        this.electricPrices.forEach(electric=>{
          this.electricPricesCopy.push(GeneralInfoElectricProduct.clone(electric));
        });

      },error => {

      }
    )
  }

  modify(type: string) {
    
  }
}
