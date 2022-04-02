import { Component, OnInit } from '@angular/core';
import {ProductList} from "../../../../../../models/offers/ProductList";
import {ClassicProduct, PriceClassicDetails, PriceClassic} from "../../../../../../models/offers/Product";



@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.css']
})
export class OfferComponent implements OnInit {
  currentPage: number = 1;
  currentNav: string = 'standard';
  productList: ProductList = new ProductList([],0);
  isBikeActive: boolean = true;

  constructor() {
  }

  ngOnInit(): void {
    this.isBikeActive = true;
    this.productList.products = [
      new ClassicProduct("1","assets/offer/standard/1.jpg",'Cannondale JEKYLL 1 - 29" Carbon Mountainbike - 2022 - Beetle Green',true,5.0,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
               new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("2","assets/offer/standard/2.jpg",'Cannondale MAVARO NEO 5 Plus - Electric City Bike - 2021 - black pearl',true,4.9,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("3","assets/offer/standard/3.jpg",'Parapera ANEMOS - Carbon Gravel Bike - 2022',true,3.5,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("4","assets/offer/standard/4.jpg",'FOCUS JAM 6.8 - 29" Mountainbike - 2022 - sky grey',true,5.0,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("5","assets/offer/standard/5.jpg",'Rennstahl 853 Pinion - 650B Trekking Bike - 2022',true,3.9,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("6","assets/offer/standard/6.jpg",'CUBE KATHMANDU Pro - Trekking Bike - 2022 - grey/black A00',true,5.0,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("7","assets/offer/standard/7.jpg",'Diamant Zing+ - Women Trekking E-Bike - 2022 - white',true,4.0,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("8","assets/offer/standard/8.jpg",'Cannondale MOTERRA NEO Carbon SE - 29" MTB E-Bike - 2021 - Stealth Gray',true,3.0,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("9","assets/offer/standard/9.jpg",'CCUBE HYDE Pro - Urbanbike with Belt Drive - 2022 - deepblue/silver A00',true,2.0,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
      new ClassicProduct("10","assets/offer/standard/10.jpg",'Diamant ELAN SUPER DELUXE - Men Trekkingbike - 2022 - Dravit grey metallic',true,4.5,
        [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
          new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])]),
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

  changeStateButton() {
    this.isBikeActive = !this.isBikeActive;
  }

  getPrice(prices: PriceClassic[]) {
    let localPrices: PriceClassic[];
    if(this.isBikeActive) localPrices = prices.filter(bike => bike.bikeType == 'classic');
    else localPrices = prices.filter(bike => bike.bikeType == 'electric');
    localPrices[0]?.priceBikeDetails.sort((a, b) => a.price < b.price ? -1 : a.price > b.price ? 1 : 0)
    return localPrices[0]?.priceBikeDetails[0]?.price;
  }


  getBikeTypeName() {
    if(this.isBikeActive) return 'classic';
    return 'electric';
  }
}
