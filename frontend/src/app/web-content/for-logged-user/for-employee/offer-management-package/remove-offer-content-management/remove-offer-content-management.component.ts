import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../../../../shared/offer.service";
import {SearchBikeResponse} from "../../../../../../models/offers/ProductGeneralOfferResponse";
import {ImageFromByteSanitizerService} from "../../../../../../shared/ImageFromByteSanitizer.service";
import {
  DetailBikeInformationResponse
} from "../../../../../../models/detail-information/response/DetailBikeInformationResponse";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  PopupInformationViewComponent
} from "../../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";

@Component({
  selector: 'app-remove-offer-content-management',
  templateUrl: './remove-offer-content-management.component.html',
  styleUrls: ['./remove-offer-content-management.component.css']
})
export class RemoveOfferContentManagementComponent implements OnInit {

  keyword = 'name';

  phone!: number;
  password!: string;

  detailOffer!: DetailBikeInformationResponse | undefined;
  bikes: SearchBikeResponse[] = []
  bike!: SearchBikeResponse;

  isErrorActive: boolean = false;
  errorMsg: string = '';

  constructor(private offerService: OfferService, private imageFromByteSanitizer: ImageFromByteSanitizerService, private ngbModal: NgbModal) {
  }

  ngOnInit(): void {
    this.offerService.getSearchBikes(true).subscribe(
      data => {
        this.bikes = data;
        for (let i = 0; i < this.bikes.length; i++) {
          let objectURL = 'data:image/png;base64,' + this.bikes[i].image;
          this.bikes[i].imageSafeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        }
      }, error => {
        this.isErrorActive= true;
        this.errorMsg = error.error;
      }
    )
  }

  search($event: SearchBikeResponse) {
    this.bike = $event;
    this.offerService.getDetailBikeInformation($event.id.toString(), true).subscribe(
      data => {
        this.detailOffer = data;
        let objectURL = 'data:image/png;base64,' + this.detailOffer.image;
        let safeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        this.detailOffer.imageSafeUrl = safeUrl;

      }, error => {
        this.isErrorActive= true;
        this.errorMsg = error.error;
      }
    )
  }

  getActivityValue() {
    return this.bike.active ? 'Aktywna' : 'Nieaktywna';
  }

  changeOfferActivity() {
    this.offerService.changeOfferActivity(this.bike.id).subscribe(
      data=>{
        const modalRef = this.ngbModal.open(PopupInformationViewComponent);
        modalRef.componentInstance.message = data.message;
        modalRef.closed.subscribe(
          data=>{
            this.ngOnInit()
            this.detailOffer = undefined;
            this.isErrorActive= false;
            this.errorMsg = '';
          }
        )
      },error => {
        this.isErrorActive= true;
        this.errorMsg = error.error;
      }
    )
  }
}
