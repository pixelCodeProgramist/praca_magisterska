import {Component, OnInit} from '@angular/core';
import {DatePickerOptions, DateRange} from "@ngx-tiny/date-picker";
import * as plLocale from 'date-fns/locale/pl'
import {ActivatedRoute, Router} from "@angular/router";
import {OfferService} from "../../../../../../shared/offer.service";
import {
  DetailBikeInformationResponse
} from "../../../../../../models/detail-information/response/DetailBikeInformationResponse";
import {ImageFromByteSanitizerService} from "../../../../../../shared/ImageFromByteSanitizer.service";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  id!: string;

  bikeDetail!: DetailBikeInformationResponse;

  public chosenDate!: Date | DateRange;


  options: DatePickerOptions = {
    locale: {plLocale},
    displayFormat: "dd-MM-yyyy",
    firstCalendarDay: 1,
  };
  selectedFrameOption!: string;
  selectedTimeOption!: string;
  withBikeTripCheckbox!: boolean;
  selectedHourBeginTripOption!: string;

  constructor(private route: ActivatedRoute, private router: Router, private offerService: OfferService, private imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.queryParams["id"];
    if (this.id == undefined) this.router.navigate(['offer']);
    this.offerService.getDetailBikeInformation(this.id).subscribe(
      data => {
        this.bikeDetail = data;
        this.selectedFrameOption = this.bikeDetail.frames[0];
        let objectURL = 'data:image/png;base64,' + this.bikeDetail.image;
        let safeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        this.bikeDetail.safeUrl = safeUrl;
      }
    )

    let today = new Date();
    today.setDate(today.getDate() - 1);
    this.options.minDate = today;


  }

  onRatingSet($event: number) {
    // if (this.productDetails.canGrade) {
    //
    // }
  }

  getCorrectGradeInPolishForm(gradesNumber: number) {
    if (gradesNumber == 1) return 'ocena';
    if (gradesNumber >= 2 && gradesNumber <= 4) return 'oceny';
    if (gradesNumber > 20 && gradesNumber % 10 >= 2 && gradesNumber % 10 <= 4) return 'oceny';
    return 'ocen';
  }

  getPrice() {
    let price = this.bikeDetail.timePriceDtoList
      .filter(detail => detail.time == this.selectedTimeOption)
      .map(detail => detail.price)[0]
    if (this.withBikeTripCheckbox) price += this.bikeDetail.guidePrice;
    return price;
  }

  changeTripState() {
    this.withBikeTripCheckbox = !this.withBikeTripCheckbox;
  }

  fillBeginHours() {
    // this.productTimeDetails = new ProductTimeDetails(['06:00', '06:30', '07:00', '07:30', '08:00', '08:30',
    //     '09:00', '09:30', '10:00', '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00', '14:30', '15:00', '15:30'],
    //   )
  }

  onChangeDate(date: Date | DateRange) {
    this.chosenDate = date;
    this.selectedTimeOption = '-';


  }
}
