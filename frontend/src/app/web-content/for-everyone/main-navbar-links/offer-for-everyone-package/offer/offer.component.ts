import { Component, OnInit } from '@angular/core';
import {ProductList} from "../../../../../models/offers/ProductList";
import {Product} from "../../../../../models/offers/Product";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css']
})
export class OfferComponent implements OnInit {
  currentPage: number = 1;
  currentNav: string = 'standard';
  productList: ProductList = new ProductList([],0);

  constructor() {
  }

  ngOnInit(): void {
    this.productList.products = [
      new Product("1","assets/offer/standard/1.jpg",'Cannondale JEKYLL 1 - 29" Carbon Mountainbike - 2022 - Beetle Green',10.00,true,5.0),
      new Product("2","assets/offer/standard/2.jpg",'Cannondale MAVARO NEO 5 Plus - Electric City Bike - 2021 - black pearl',10.00,true,4.9),
      new Product("3","assets/offer/standard/3.jpg",'Parapera ANEMOS - Carbon Gravel Bike - 2022',10.00,true,3.5),
      new Product("4","assets/offer/standard/4.jpg",'FOCUS JAM 6.8 - 29" Mountainbike - 2022 - sky grey',10.00,true,5.0),
      new Product("5","assets/offer/standard/5.jpg",'Rennstahl 853 Pinion - 650B Trekking Bike - 2022',10.00,true,3.9),
      new Product("6","assets/offer/standard/6.jpg",'CUBE KATHMANDU Pro - Trekking Bike - 2022 - grey/black A00',10.00,true,5.0),
      new Product("7","assets/offer/standard/7.jpg",'Diamant Zing+ - Women Trekking E-Bike - 2022 - white',10.00,true,4.0),
      new Product("8","assets/offer/standard/8.jpg",'Cannondale MOTERRA NEO Carbon SE - 29" MTB E-Bike - 2021 - Stealth Gray',10.00,true,3.0),
      new Product("9","assets/offer/standard/9.jpg",'CCUBE HYDE Pro - Urbanbike with Belt Drive - 2022 - deepblue/silver A00',10.00,true,2.0),
      new Product("10","assets/offer/standard/10.jpg",'Diamant ELAN SUPER DELUXE - Men Trekkingbike - 2022 - Dravit grey metallic',10.00,true,4.5),
    ];


  }

  clickNavItem($event: MouseEvent) {
    let target = $event.target as HTMLTextAreaElement;
    this.currentNav = target.id;
    this.currentPage = 1;
    document.getElementById('standard')!.className = 'nav-link';
    document.getElementById('plus')!.className = 'nav-link';
    document.getElementById('premium')!.className = 'nav-link';
    document.getElementById('accessories')!.className = 'nav-link';
    target.className = "nav-link active"
  }

  getCurrentPage(page: number) {
    this.currentPage = page;
  }


  onRatingSet($event: number) {

  }
}
