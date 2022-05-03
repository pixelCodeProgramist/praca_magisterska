import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {NgbCalendar, NgbDate, NgbDateStruct, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UserService} from "../../../../../shared/user.service";
import {Branch} from "../../../../../models/general-information/response/GeneralInformationResponse";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {DetailEmployeeResponse} from "../../../../../models/detail-user/DetailEmployeeResponse";
import {
  PopupInformationViewComponent
} from "../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";

@Component({
  selector: 'app-add-employee-content-management',
  templateUrl: './add-employee-content-management.component.html',
  styleUrls: ['./add-employee-content-management.component.css']
})
export class AddEmployeeContentManagementComponent implements OnInit {

  public addEmployeeFormGroup!: FormGroup;
  birthDay!: NgbDateStruct;
  employeeDate!: NgbDateStruct;
  today:NgbDate = this.calendar.getToday();
  phone!: number;
  password!: string;
  branches: Branch[] = [];

  detailUserResponse: DetailEmployeeResponse = new DetailEmployeeResponse()

  isErrorActive: boolean = false;
  errorMsg: string = '';


  constructor(private userService: UserService, private ngbModal: NgbModal, private calendar: NgbCalendar, private generalInformationService: GeneralInformationService) { }



  ngOnInit(): void {
    this.generalInformationService.getBranches().subscribe(
      data=>{
        this.branches = data;
      },error => {
        this.isErrorActive = true;
        this.errorMsg = 'Nie można pobrać oddziałów'
      }
    )
    this.addEmployeeFormGroup = new FormGroup({
      firstName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      phone: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('^[0-9]*$')]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), this.createPasswordStrengthValidator()]),
      repeatPassword : new FormControl('', [Validators.required, this.createPasswordSameValidator()]),
      email: new FormControl('', [Validators.required, Validators.email]),
      birthdayDate: new FormControl('', [Validators.required]),
      employeeDate: new FormControl('', [Validators.required, this.employeeDateValidator()]),
      street: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      zipCode: new FormControl('', [Validators.required, Validators.pattern("^\\d{2}[- ]{0,1}\\d{3}$")]),
      city: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      branch: new FormControl('', [Validators.required]),

    });
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.addEmployeeFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.addEmployeeFormGroup!.controls[controlName]?.errors != null && this.addEmployeeFormGroup!.controls[controlName].touched;
  }
  selectedBranchOption!: string;


  private createPasswordStrengthValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const value = control.value;

      if (!value) {
        return null;
      }

      const hasUpperCase = /[A-Z]+/.test(value);

      const hasLowerCase = /[a-z]+/.test(value);

      const hasNumeric = /[0-9]+/.test(value);

      const passwordValid = hasUpperCase && hasLowerCase && hasNumeric;

      return !passwordValid ? {passwordStrength: true} : null;
    }

  }


  private createPasswordSameValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const value = control.value;

      const passwordValue = this.addEmployeeFormGroup?.controls['password']?.value;
      if (!value) {
        return null;
      }
      const passwordValid = passwordValue == value;


      return !passwordValid ? {passwordNotSame: true} : null;
    }
  }

  submit() {
    if(this.addEmployeeFormGroup.valid) {
      this.detailUserResponse.password = this.password;
      this.detailUserResponse.phone = this.phone;
      this.detailUserResponse.birthDay =  new Date(this.birthDay?.year,this.birthDay?.month-1, this.birthDay?.day)
      this.detailUserResponse.employeeDate =  new Date(this.employeeDate?.year,this.employeeDate?.month-1, this.employeeDate?.day)
      this.detailUserResponse.branchId = Number(this.selectedBranchOption)
      this.userService.addEmployee(this.detailUserResponse).subscribe(
        data=>{
          const modalRef = this.ngbModal.open(PopupInformationViewComponent);
          modalRef.componentInstance.message = data.message;
          this.addEmployeeFormGroup.reset();
        },error => {
          this.isErrorActive = true;
          this.errorMsg = error.error;
        }
      )
    }



  }

  changeStatusOfAdminCheckbox() {
    this.detailUserResponse.isAdmin = !this.detailUserResponse.isAdmin
    console.log(this.detailUserResponse.isAdmin)
  }

  private employeeDateValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const value = control.value;

      if (!value) {
        return null;
      }
      let birthdayC = this.addEmployeeFormGroup.get('birthdayDate')?.value as NgbDateStruct;

      let birthday = new Date(birthdayC.year,birthdayC.month-1, birthdayC.day)

      let employeeDay = new Date(value.year,value.month-1, value.day)

      const employeeDateValid = birthday.getTime()<employeeDay.getTime();


      return !employeeDateValid ? {employeeDateValid: true} : null;
    }

  }
}
