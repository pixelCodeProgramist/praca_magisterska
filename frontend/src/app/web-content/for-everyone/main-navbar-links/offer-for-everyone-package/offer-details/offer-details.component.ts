import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DatePickerOptions, DateRange} from "@ngx-tiny/date-picker";
import * as plLocale from 'date-fns/locale/pl'
import {ActivatedRoute, Router} from "@angular/router";
import {OfferService} from "../../../../../../shared/offer.service";
import {
  DetailBikeInformationResponse
} from "../../../../../../models/detail-information/response/DetailBikeInformationResponse";
import {ImageFromByteSanitizerService} from "../../../../../../shared/ImageFromByteSanitizer.service";
import {UserService} from "../../../../../../shared/user.service";
import {
  DateAndHourOfReservationRequest
} from "../../../../../../models/offers/pre-order/request/DateAndHourOfReservationRequest";
import {AvailableHoursResponse} from "../../../../../../models/offers/pre-order/response/AvailableHoursResponse";
import {AccessoryInOrder} from "../../../../../../models/offers/pre-order/response/AccessoryInOrder";
import {GradeRequest} from "../../../../../../models/offers/pre-order/request/GradeRequest";
import {NgxStarsComponent} from "ngx-stars";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit, AfterViewInit {
  id!: string;

  bikeDetail: DetailBikeInformationResponse = new DetailBikeInformationResponse();

  public chosenDate!: Date | DateRange;

  dateAndHourOfReservationRequest: DateAndHourOfReservationRequest = new DateAndHourOfReservationRequest();

  options: DatePickerOptions = {
    locale: {plLocale},
    displayFormat: "dd-MM-yyyy",
    firstCalendarDay: 1,
  };
  selectedFrameOption!: string;
  selectedTimeOption!: string;
  withBikeTripCheckbox!: boolean;
  selectedHourBeginTripOption!: string;
  productTimeDetails: AvailableHoursResponse = new AvailableHoursResponse();
  accessoriesInOrder: AccessoryInOrder[] = [];
  withAccessoryCheckbox: boolean = false;
  selectedAccessoryOption!: AccessoryInOrder;
  canGrade: boolean = false;
  @ViewChild(NgxStarsComponent)
  starsComponent!: NgxStarsComponent;

  constructor(private route: ActivatedRoute, private router: Router, private offerService: OfferService,
              private imageFromByteSanitizer: ImageFromByteSanitizerService, public userService: UserService) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.queryParams["id"];
    if (this.id == undefined) this.router.navigate(['offer']);
    if(this.userService.isLoggedIn()){

      this.offerService.canGrade(Number(this.id)).subscribe(
        data=>{
          this.canGrade = data;
        },error => {

        }
      )
    }



    this.options.minDate = new Date();


  }

  onRatingSet($event: number) {
    //console.log(this.id)
    if(this.canGrade) {
      this.offerService.grade(new GradeRequest(Number(this.id), $event)).subscribe(
        data=>{
          this.canGrade = false;
          this.ngAfterViewInit()
        },error => {

        }
      )
    }
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
    if (this.withAccessoryCheckbox && this.selectedAccessoryOption) price += this.selectedAccessoryOption.price;
    return price;
  }

  changeTripState() {
    this.withBikeTripCheckbox = !this.withBikeTripCheckbox;
  }

  fillBeginHours() {
    this.dateAndHourOfReservationRequest.reservationRange = this.selectedTimeOption;
    this.withAccessoryCheckbox = false;

    this.offerService.getAvailableHours(this.dateAndHourOfReservationRequest).subscribe(
      data=>{
        this.productTimeDetails = data;
      },error => {

      }
    )

  }

  onChangeDate(date: Date | DateRange) {
    this.chosenDate = date;
    this.selectedTimeOption = '-';
    this.dateAndHourOfReservationRequest.reservationTime = new Date(this.chosenDate.toString());


  }

  onFrameChange($event: any) {
    this.selectedTimeOption = '-';
    this.selectedHourBeginTripOption = '-';
    this.withBikeTripCheckbox = false;
    this.withAccessoryCheckbox = false;
    this.dateAndHourOfReservationRequest.frame = this.selectedFrameOption;

  }

  changeAccessoryState() {
    this.withAccessoryCheckbox = !this.withAccessoryCheckbox;
    if(this.withAccessoryCheckbox) {

      let rangeInt = Number(this.selectedTimeOption.replace('godzina','1')
        .replace('doba','24').replace('dzień','5')
        .replace('do ','').replace(' h',''));
      this.offerService.getAccessoryDetailOfferResponse(rangeInt).subscribe(
        data=>{
          this.accessoriesInOrder = data;
        },error => {

        }
      )
    }

  }

  selectAccessory() {
    console.log(this.selectedAccessoryOption)
  }

  ngAfterViewInit(): void {
    this.offerService.getDetailBikeInformation(this.id).subscribe(
      data => {
        this.bikeDetail = data;
        this.selectedFrameOption = this.bikeDetail.frames[0];
        let objectURL = 'data:image/png;base64,' + this.bikeDetail.image;
        let safeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        this.bikeDetail.safeUrl = safeUrl;
        this.dateAndHourOfReservationRequest.bikeId = this.bikeDetail.id;
        this.dateAndHourOfReservationRequest.frame = this.selectedFrameOption;
        this.starsComponent.setRating(this.bikeDetail.rating)
      }
    )
  }
}


