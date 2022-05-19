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
import {ErrorHandler} from "../../../../../../shared/ErrorHandler";

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
  loading: boolean = false;

  constructor(private offerService: OfferService, private imageFromByteSanitizer: ImageFromByteSanitizerService, private ngbModal: NgbModal) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.offerService.getSearchBikes(true).subscribe(
      data => {
        this.loading = false;
        this.bikes = data;
        for (let i = 0; i < this.bikes.length; i++) {
          let objectURL = 'data:image/png;base64,' + this.bikes[i].image;
          this.bikes[i].imageSafeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        }
      }, error => {
        this.loading = false;
        this.isErrorActive= true;
        let errorHandler: ErrorHandler = new ErrorHandler();
        this.errorMsg = errorHandler.handle(error,this.errorMsg)
      }
    )
  }

  search($event: SearchBikeResponse) {
    this.bike = $event;
    this.loading = true;
    this.offerService.getDetailBikeInformation($event.id.toString(), true).subscribe(
      data => {
        this.loading = false;
        this.detailOffer = data;
        let objectURL = 'data:image/png;base64,' + this.detailOffer.image;
        let safeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        this.detailOffer.imageSafeUrl = safeUrl;

      }, error => {
        this.loading = false;
        this.isErrorActive= true;
        let errorHandler: ErrorHandler = new ErrorHandler();
        this.errorMsg = errorHandler.handle(error,this.errorMsg)
      }
    )
  }

  getActivityValue() {
    return this.bike.active ? 'Aktywna' : 'Nieaktywna';
  }

  changeOfferActivity() {
    this.loading = true;
    this.offerService.changeOfferActivity(this.bike.id).subscribe(
      data=>{
        this.loading = false;
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
        this.loading = false;
        this.isErrorActive= true;
        let errorHandler: ErrorHandler = new ErrorHandler();
        this.errorMsg = errorHandler.handle(error,this.errorMsg)
      }
    )
  }
}
