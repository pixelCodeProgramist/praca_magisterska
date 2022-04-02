import { Component, OnInit } from '@angular/core';
import {ProductDetails, ProductTimeDetails} from "../../../../../../models/offers/ProductDetails";
import {DatePickerOptions, DateRange} from "@ngx-tiny/date-picker";
import * as plLocale from 'date-fns/locale/pl'
import {PriceClassic, PriceClassicDetails} from "../../../../../../models/offers/Product";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  id!: string;
  productDetails!: ProductDetails;
  public chosenDate!: Date | DateRange;
  isBikeActive: boolean = true;
  filteredPriceClassic!: PriceClassic;
  options: DatePickerOptions = {
    locale: {plLocale},
    displayFormat: "dd-MM-yyyy",
    firstCalendarDay: 1,
  };
  selectedFrameOption!: string;
  selectedTimeOption!: string;
  withBikeTripCheckbox!: boolean;
  productTimeDetails!: ProductTimeDetails;
  selectedHourBeginTripOption!: string;

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.queryParams["id"];
    if(this.id == undefined) this.router.navigate(['offer']);
    let today = new Date();
    today.setDate(today.getDate()-1);
    this.options.minDate = today;
    this.isBikeActive = this.route.snapshot.queryParams["type"] == undefined || this.route.snapshot.queryParams["type"] == 'classic' ? true:
      this.route.snapshot.queryParams["type"] != 'electric';

    this.productDetails = new ProductDetails("1","assets/offer/standard/1.jpg",'Cannondale JEKYLL 1 - 29" Carbon Mountainbike - 2022 - Beetle Green',true,5.0,
      [new PriceClassic('classic',[new PriceClassicDetails(10.00,'hour'),new PriceClassicDetails(40.00,'day'),new PriceClassicDetails(50.00,'dayAndNight')]),
        new PriceClassic('electric',[new PriceClassicDetails(120.00,'threeHours'),new PriceClassicDetails(180.00,'fiveHours'),new PriceClassicDetails(250.00,'dayAndNight')])],
      25,true,['S','M', 'L', 'XL']);
    this.selectedFrameOption = this.productDetails.availableFrameSize[0];
  }

  onRatingSet($event: number) {
    if(this.productDetails.canGrade) {

    }
  }

  getCorrectGradeInPolishForm(gradesNumber: number) {
    if(gradesNumber == 1) return 'ocena';
    if(gradesNumber >= 2 && gradesNumber <= 4) return  'oceny';
    if(gradesNumber>20 &&gradesNumber%10 >= 2 && gradesNumber%10 <= 4) return  'oceny';
    return 'ocen';
  }



  onChangeDate(date: Date | DateRange) {
    this.chosenDate = date;
    let type = this.isBikeActive?'classic':'electric';
    this.filteredPriceClassic = this.productDetails.prices.filter(e=>e.bikeType==type)[0];
    this.selectedTimeOption = '-';
  }

  getPrice() {
    let price = this.filteredPriceClassic.priceBikeDetails
      .filter(detail=>detail.range==this.selectedTimeOption)
      .map(detail=> detail.price)
    return price[0];
  }

  changeStateButton() {
    this.isBikeActive = !this.isBikeActive;
    let type = this.isBikeActive?'classic':'electric';
    this.filteredPriceClassic = this.productDetails.prices.filter(e=>e.bikeType==type)[0];
    this.selectedTimeOption = '-';
    this.router.navigate(['.'], { relativeTo: this.route, queryParams: { id: this.id, type: type}});

  }

  getRange(range: string) {
    if(range == 'hour') return 'godzina';
    if(range == 'day') return 'dzień';
    if(range == 'dayAndNight') return 'doba';
    if(range == 'threeHours') return '3 godziny';
    if(range == 'fiveHours') return '5 godzin';
    return '';
  }

  changeTripState() {
    this.withBikeTripCheckbox = ! this.withBikeTripCheckbox;
  }

  fillBeginHours() {
    this.productTimeDetails = new ProductTimeDetails(['06:00','06:30','07:00','07:30','08:00','08:30',
      '09:00','09:30','10:00','10:30','11:00','11:30', '12:00','12:30','13:00','13:30','14:00','14:30', '15:00','15:30'],
      this.productDetails)
  }
}
