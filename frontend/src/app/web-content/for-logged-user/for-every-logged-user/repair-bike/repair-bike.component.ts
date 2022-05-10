import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BikeRepairRequest} from "../../../../../models/order/request/BikeRepairRequest";
import {NgbCalendar, NgbDate, NgbDateStruct, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {OrderService} from "../../../../../shared/order.service";

@Component({
  selector: 'app-repair-bike',
  templateUrl: './repair-bike.component.html',
  styleUrls: ['./repair-bike.component.css']
})
export class RepairBikeComponent implements OnInit {
  isErrorActive: boolean = false;
  errorMsg: string = '';
  repairBike: BikeRepairRequest = new BikeRepairRequest();
  today:NgbDate = this.calendar.getNext(this.calendar.getToday());
  beginDate!: NgbDateStruct;
  public addRepairBikeFormGroup!: FormGroup;



  public checkError = (controlName: string, errorName: string) => {
    return this.addRepairBikeFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.addRepairBikeFormGroup!.controls[controlName]?.errors != null && this.addRepairBikeFormGroup!.controls[controlName].touched;
  }




  constructor(public userService: UserService, private ngbModal: NgbModal, private calendar: NgbCalendar, private orderService: OrderService) { }

  ngOnInit(): void {
    this.addRepairBikeFormGroup = new FormGroup({
      defectName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      beginDate: new FormControl('', [Validators.required]),
      descriptionName: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(1000)]),
    });
  }

  submit() {
    if(this.addRepairBikeFormGroup.valid) {
      this.repairBike.beginDate = new Date(this.beginDate.year, this.beginDate.month-1, this.beginDate.day);
      this.orderService.makeOrderBikeRepair(this.repairBike).subscribe(
        data=>{
          window.location.href = data.message;
        },error => {
          this.isErrorActive = true;
          this.errorMsg = error.error.order;
        }
      )
    }
  }
}
