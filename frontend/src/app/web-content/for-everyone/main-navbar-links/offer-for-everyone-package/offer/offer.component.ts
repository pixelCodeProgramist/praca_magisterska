import {Component, OnInit} from '@angular/core';
import {ProductList} from "../../../../../../models/offers/ProductList";
import {
  AccessoryGeneralOfferResponse,
  ProductGeneralOfferResponse
} from "../../../../../../models/offers/ProductGeneralOfferResponse";
import {OfferService} from "../../../../../../shared/offer.service";
import {ImageFromByteSanitizerService} from "../../../../../../shared/ImageFromByteSanitizer.service";
import {ViewportScroller} from "@angular/common";


@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css']
})
export class OfferComponent implements OnInit {
  currentPage: number = 1;
  currentNav: string = 'standard';
  productList: ProductList = new ProductList([], 0);
  isBikeActive: boolean = true;
  productGeneralOfferResponse: ProductGeneralOfferResponse = new ProductGeneralOfferResponse();
  accessoryGeneralOfferResponse: AccessoryGeneralOfferResponse = new AccessoryGeneralOfferResponse();

  constructor(private offerService: OfferService, private imageFromByteSanitizer: ImageFromByteSanitizerService, private viewportScroller: ViewportScroller) {
  }

  loadProducts() {
    if (this.isBikeActive && this.currentNav == 'bike') this.currentNav = 'standard';
    if (this.currentNav != 'accessories') {
      this.offerService.getBikeGeneralOfferInformation(this.currentNav, this.currentPage - 1, this.isBikeActive ? 'classic' : 'electric')!.subscribe(
        data => {
          this.productGeneralOfferResponse = data;
          for (let i = 0; i < this.productGeneralOfferResponse.products.length; i++) {
            let objectURL = 'data:image/png;base64,' + this.productGeneralOfferResponse.products[i].image;
            this.productGeneralOfferResponse.products[i].imageSafeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
          }
        }, error => {

        }
      )
    } else {
      this.offerService.getAccessoryGeneralOfferResponse(this.currentNav, this.currentPage - 1).subscribe(
        data=>{
          this.accessoryGeneralOfferResponse = data;
          for (let i = 0; i < this.accessoryGeneralOfferResponse.products.length; i++) {
            let objectURL = 'data:image/png;base64,' + this.accessoryGeneralOfferResponse.products[i].image;
            this.accessoryGeneralOfferResponse.products[i].imageSafeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
          }
        },error => {

        }
      )
    }

  }

  ngOnInit(): void {
    this.isBikeActive = true;
    this.loadProducts();
  }

  clickNavItem($event: MouseEvent) {
    let target = $event.target as HTMLTextAreaElement;
    this.currentNav = target.id;
    this.currentPage = 1;
    if (this.isBikeActive) {
      document.getElementById('standard')!.className = 'nav-link';
      document.getElementById('plus')!.className = 'nav-link';
      document.getElementById('premium')!.className = 'nav-link';
      document.getElementById('accessories')!.className = 'nav-link';
    } else {
      document.getElementById('bike')!.className = 'nav-link';
      document.getElementById('accessories')!.className = 'nav-link';
    }

    target.className = "nav-link active"
    this.loadProducts();
  }

  clickOnPage(page: number) {
    this.currentPage = page;
    this.loadProducts();
    this.viewportScroller.scrollToPosition([0, 0]);
  }


  onRatingSet($event: number) {

  }

  changeStateButton() {
    this.isBikeActive = !this.isBikeActive;
    this.loadProducts();
  }


  getBikeTypeName() {
    if (this.isBikeActive) return 'classic';
    return 'electric';
  }

  getCurrentNavInRightForm(currentNav: string) {
    if (currentNav.toLowerCase() == 'accessories') return 'Akcesoria';
    return currentNav;
  }
}
