import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../../../../shared/offer.service";
import {GeneralInfoClassicPrice} from "../../../../../../models/GeneralInfoClassicProduct";
import {GeneralInfoElectricProduct} from "../../../../../../models/GeneralInfoElectricProduct";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  PopupInformationViewComponent
} from "../../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";

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

  isErrorClassicActive: boolean = false;
  errorClassicMsg: string = '';

  isErrorElectricActive: boolean = false;
  errorElectricMsg: string = '';

  constructor(private offerService: OfferService, private ngbModal: NgbModal) { }

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
    if(type == 'classic') {
      if(this.verifyClassic()) {
        this.isErrorClassicActive = false;
        this.errorClassicMsg = '';
        this.offerService.modifyClassicPrices(this.classicPrices).subscribe(
          data=>{
            const modalRef = this.ngbModal.open(PopupInformationViewComponent);
            modalRef.componentInstance.message = data.message;
            modalRef.closed.subscribe(
              data=>{
                this.ngOnInit()
                this.isErrorElectricActive= false;
                this.isErrorClassicActive= false;
                this.errorElectricMsg = '';
                this.errorClassicMsg = '';
              }
            )
          },error => {

          }
        )
      }
    }

    if(type == 'electric') {
      if(this.verifyElectric()) {
        this.isErrorElectricActive = false;
        this.errorElectricMsg = '';
        this.offerService.modifyElectricPrices(this.electricPrices).subscribe(
          data=>{
            const modalRef = this.ngbModal.open(PopupInformationViewComponent);
            modalRef.componentInstance.message = data.message;
            modalRef.closed.subscribe(
              data=>{
                this.ngOnInit()
                this.isErrorElectricActive= false;
                this.isErrorClassicActive= false;
                this.errorElectricMsg = '';
                this.errorClassicMsg = '';
              }
            )
          },error => {

          }
        )
      }
    }

  }

  verifyClassic() {
    let i = 0;
    let iter = 0;
    for(let classic of this.classicPrices) {
      if (GeneralInfoClassicPrice.isEquals(classic,this.classicPricesCopy[i])) {
        iter++
      }

      if (!GeneralInfoClassicPrice.isValid(classic)) {
        this.isErrorClassicActive = true;
        this.errorClassicMsg = 'Ceny nie są liczbami'
        return false;
      }
      i++;
    }
    if(iter==this.classicPrices.length) {
      this.isErrorClassicActive = true;
      this.errorClassicMsg = 'Dane się nie zmieniły'
      return false;
    } else {
      return true;
    }
  }

  verifyElectric() {
    let i = 0;
    let iter = 0;
    for(let electric of this.electricPrices) {
      if (GeneralInfoElectricProduct.isEquals(electric,this.electricPricesCopy[i])) {
        iter++
      }

      if (!GeneralInfoElectricProduct.isValid(electric)) {
        this.isErrorElectricActive = true;
        this.errorElectricMsg = 'Ceny nie są liczbami'
        return false;
      }
      i++;
    }
    if(iter==this.electricPrices.length) {
      this.isErrorElectricActive = true;
      this.errorElectricMsg = 'Dane się nie zmieniły'
      return false;
    } else {
      return true;
    }
  }
}
