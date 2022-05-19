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
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {OrderRequest} from "../../../../../../models/order/request/OrderRequest";
import {OrderService} from "../../../../../../shared/order.service";
import {ErrorHandler} from "../../../../../../shared/ErrorHandler";

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
  isErrorActive: boolean = false;
  errorMsg: string = '';
  loading: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router, private offerService: OfferService,
              private imageFromByteSanitizer: ImageFromByteSanitizerService, public userService: UserService,
              private ngbModal: NgbModal, private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.queryParams["id"];
    if (this.id == undefined) this.router.navigate(['offer']);

    if (this.userService.isLoggedIn()) {
      this.loading = true;
      this.offerService.canGrade(Number(this.id)).subscribe(
        data => {
          this.loading = false;
          this.canGrade = data;
        }, error => {
          this.loading = false;
        }
      )
    }


    this.options.minDate = new Date();


  }

  onRatingSet($event: number) {
    if (this.canGrade) {
      this.offerService.grade(new GradeRequest(Number(this.id), $event)).subscribe(
        data => {
          this.canGrade = false;
          this.ngAfterViewInit()
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
    this.dateAndHourOfReservationRequest.bikeId = Number(this.id);
    this.withAccessoryCheckbox = false;
    this.loading = true;
    this.offerService.getAvailableHours(this.dateAndHourOfReservationRequest).subscribe(
      data => {
        this.productTimeDetails = data;
        this.loading = false;
      }, error => {
        this.loading = false;
        this.isErrorActive = true
        let errorHandler: ErrorHandler = new ErrorHandler();
        this.errorMsg = errorHandler.handle(error,this.errorMsg)
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

  convertRangeToNumber(dayReplace: string) {
    return Number(this.selectedTimeOption.replace('godzina', '1')
      .replace('doba', '24').replace('dzień', dayReplace)
      .replace('do ', '').replace(' h', ''));
  }

  changeAccessoryState() {
    this.withAccessoryCheckbox = !this.withAccessoryCheckbox;

    if (this.withAccessoryCheckbox) {
      this.loading = true;
      let rangeInt = this.convertRangeToNumber('5');

      this.offerService.getAccessoryDetailOfferResponse(rangeInt).subscribe(
        data => {
          this.accessoriesInOrder = data;
          this.loading = false;
          if(this.accessoriesInOrder.length==0) {
            this.isErrorActive = true
            this.errorMsg = 'Brak akcesorium'
          }

        }, error => {
          this.loading = false;
          this.isErrorActive = true
          let errorHandler: ErrorHandler = new ErrorHandler();
          this.errorMsg = errorHandler.handle(error,this.errorMsg)

        }
      )
    }

  }

  ngAfterViewInit(): void {
    this.loading = true;
    this.offerService.getDetailBikeInformation(this.id).subscribe(
      data => {
        this.bikeDetail = data;
        this.selectedFrameOption = this.bikeDetail.frames[0].frameSize;
        let objectURL = 'data:image/png;base64,' + this.bikeDetail.image;
        let safeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        this.bikeDetail.safeUrl = safeUrl;
        this.dateAndHourOfReservationRequest.bikeId = this.bikeDetail.id;
        this.dateAndHourOfReservationRequest.frame = this.selectedFrameOption;
        this.starsComponent.setRating(this.bikeDetail.rating)
        this.loading = false;
      }, error => {
        if (error.error.offer) {
          if (error.error.offer.includes('does not exists')) this.router.navigate(["404"]);
        }
        this.loading = false;
        this.isErrorActive = true
        let errorHandler: ErrorHandler = new ErrorHandler();
        this.errorMsg = errorHandler.handle(error,this.errorMsg)

      }
    )
  }

  order() {
    if (!this.isEmpty(this.selectedFrameOption) && !this.isEmpty(this.selectedTimeOption) && !this.isEmpty(this.selectedHourBeginTripOption)) {
      let beginDate = new Date(this.dateAndHourOfReservationRequest.reservationTime);
      let endDate = new Date(beginDate);
      let beginHour = Number(this.selectedHourBeginTripOption.split(':')[0]);
      let rangeInt = this.convertRangeToNumber(String(18 - beginHour));


      beginDate.setHours(beginHour);
      endDate.setHours(beginHour + rangeInt);


      let orderRequest: OrderRequest =
        new OrderRequest(Number(this.id), this.selectedFrameOption, beginDate, endDate,
          this.selectedAccessoryOption?.id, this.getPrice(), this.withBikeTripCheckbox);
      this.loading = true;
      this.orderService.makeOrder(orderRequest).subscribe(
        data => {
          window.location.href = data.message;
          this.loading = false;
        }, error => {
          this.loading = false;
          this.isErrorActive = true
          let errorHandler: ErrorHandler = new ErrorHandler();
          this.errorMsg = errorHandler.handle(error,this.errorMsg)
        }
      )
    }

  }

  isEmpty(str: string) {
    return str.trim().length == 0;
  }
}


