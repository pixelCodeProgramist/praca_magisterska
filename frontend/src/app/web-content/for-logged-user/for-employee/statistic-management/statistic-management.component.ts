import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";
import {OrderService} from "../../../../../shared/order.service";
import {AprioriResponse} from "../../../../../models/statistic/apripri/response/AprioriResponse";
import {AprioriRequest} from "../../../../../models/statistic/apripri/request/AprioriRequest";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbCalendar, NgbDate, NgbDateParserFormatter} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-statistic-management',
  templateUrl: './statistic-management.component.html',
  styleUrls: ['./statistic-management.component.css']
})
export class StatisticManagementComponent implements OnInit {

  aprioriResponse: AprioriResponse[] = [];
  aprioriRequest: AprioriRequest = new AprioriRequest();
  public aprioriRequestFormGroup!: FormGroup;
  isErrorActive: boolean = false;
  errorMsg: string = '';

  fromDate!: NgbDate | null;
  toDate!: NgbDate | null;

  constructor(public userService: UserService, private orderService: OrderService, private calendar: NgbCalendar, public formatter: NgbDateParserFormatter) {
    this.fromDate = calendar.getToday();
    this.toDate = calendar.getNext(calendar.getToday(), 'd', 10);
  }

  ngOnInit(): void {
    this.aprioriRequestFormGroup = new FormGroup({
      confidenceName: new FormControl('', [Validators.required, Validators.min(0.01), Validators.max(1)]),
      supportName: new FormControl('', [Validators.required, Validators.min(0.01), Validators.max(1)]),
    });

  }

  submit() {
    if(this.aprioriRequestFormGroup.valid) {
      this.aprioriRequest.from = new Date(<number>this.fromDate?.year, <number>this.fromDate?.month-1, this.fromDate?.day);
      this.aprioriRequest.to = new Date(<number>this.toDate?.year, <number>this.toDate?.month-1, this.toDate?.day);

      this.orderService.getAprioriStatistic(this.aprioriRequest).subscribe(
        data=>{
          this.aprioriResponse = data;
          console.log(this.aprioriResponse)
        },error => {

        }
      )
    }

  }

  public checkError = (controlName: string, errorName: string) => {
    return this.aprioriRequestFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.aprioriRequestFormGroup!.controls[controlName]?.errors != null && this.aprioriRequestFormGroup!.controls[controlName].touched;
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  hoveredDate: NgbDate | null = null;



  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) &&
      date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) { return this.toDate && date.after(this.fromDate) && date.before(this.toDate); }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) || this.isInside(date) ||
      this.isHovered(date);
  }

  validateInput(currentValue: NgbDate | null, input: string): NgbDate | null {
    const parsed = this.formatter.parse(input);
    return parsed && this.calendar.isValid(NgbDate.from(parsed)) ? NgbDate.from(parsed) : currentValue;
  }
}
