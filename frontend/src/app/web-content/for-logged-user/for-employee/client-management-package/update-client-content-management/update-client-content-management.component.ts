import { Component, OnInit } from '@angular/core';
import {DetailUserMoreResponse} from "../../../../../../models/detail-user/DetailUserMoreResponse";
import {NgbDate, NgbDateStruct, NgbInputDatepicker, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Branch} from "../../../../../../models/general-information/response/GeneralInformationResponse";
import {UserService} from "../../../../../../shared/user.service";
import {Router} from "@angular/router";
import {GeneralInformationService} from "../../../../../../shared/general-information.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {
  PopupInformationViewComponent
} from "../../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";
import {ErrorHandler} from "../../../../../../shared/ErrorHandler";

@Component({
  selector: 'app-update-client-content-management',
  templateUrl: './update-client-content-management.component.html',
  styleUrls: ['./update-client-content-management.component.css']
})
export class UpdateClientContentManagementComponent implements OnInit {

  keyword = 'firstName';

  phone!: number;
  repeatPassword!: string;

  detailClientResponse: DetailUserMoreResponse = new DetailUserMoreResponse()
  detailClientResponses: DetailUserMoreResponse[] = []

  isErrorActive: boolean = false;
  errorMsg: string = '';


  updateClientFormGroup: any;
  canToggleCalendar: boolean = false;

  today: any;


  detailClientResponseOrginal: DetailUserMoreResponse = new DetailUserMoreResponse();

  birthdayDate!: NgbDateStruct;


  branches: Branch[] = [];

  loading: boolean = false;



  constructor(private userService: UserService, private ngbModal: NgbModal, private router: Router,
              private generalInformationService: GeneralInformationService) {

  }


  ngOnInit(): void {
    this.loading = true;
    this.generalInformationService.getBranches().subscribe(
      data => {
        this.branches = data;
      }, error => {
        this.isErrorActive = true;
        this.errorMsg = 'Nie można pobrać oddziałów'
      }
    )

    this.userService.getAllClients().subscribe(
      data => {
        this.loading = false;
        this.detailClientResponses = data;
        this.detailClientResponse = this.detailClientResponses[0];

        this.setRoleCheckBox();

        this.detailClientResponse.birthDay = new Date(this.detailClientResponse.birthDay);
        this.detailClientResponse.employeeDate = new Date(this.detailClientResponse.employeeDate);
        this.detailClientResponseOrginal = DetailUserMoreResponse.cloneDeep(this.detailClientResponse)

        this.birthdayDate = new NgbDate(
          this.detailClientResponseOrginal.birthDay.getFullYear(),
          this.detailClientResponseOrginal.birthDay.getMonth() + 1,
          this.detailClientResponseOrginal.birthDay.getDate());


      }, error => {
        this.loading = false;
        localStorage.clear()
        this.router.navigate(['/', 'login']);
      }
    )

    this.setAllValidators();
  }

  setRoleCheckBox() {
    if (this.detailClientResponse.roleView.role == 'ADMIN') this.detailClientResponse.isAdmin = true;
    else this.detailClientResponse.isAdmin = false;
  }

  setEditPossibility(element: HTMLInputElement) {
    if (element.name.toLowerCase() !== 'birthday')
      element.readOnly = !element.readOnly;
    else {
      this.canToggleCalendar = !this.canToggleCalendar;
      if (this.canToggleCalendar) element.style.background = 'white';
      else element.style.background = ''
    }
  }

  canToggle(birthday: HTMLInputElement, d: NgbInputDatepicker) {
    if (this.canToggleCalendar) d.toggle();
  }

  setUntouchedIfBlocked(controlname: string, element: HTMLInputElement) {
    if (element.readOnly) {
      this.updateClientFormGroup.get(controlname).clearValidators();
      this.updateClientFormGroup.get(controlname)?.updateValueAndValidity();
    } else {
      this.setAllValidators()
      this.detailClientResponse = {...this.detailClientResponse};
    }

  }

  public checkError = (controlName: string, errorName: string) => {
    return this.updateClientFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.updateClientFormGroup!.controls[controlName]?.errors != null && this.updateClientFormGroup!.controls[controlName].touched;
  }


  private setAllValidators() {
    this.updateClientFormGroup = new FormGroup({
      firstName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      phone: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('^[0-9]*$')]),
      email: new FormControl('', [Validators.required, Validators.email]),
      street: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      zipCode: new FormControl('', [Validators.required, Validators.pattern("^\\d{2}[- ]{0,1}\\d{3}$")]),
      city: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      birthdayDate: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.minLength(8), this.createPasswordStrengthValidator()]),
      repeatPassword: new FormControl('', [this.createPasswordSameValidator()]),
    });
    this.updateInputValues();


  }

  private updateInputValues() {

    this.updateClientFormGroup.controls['firstName'].value = this.detailClientResponse.firstName;
    this.updateClientFormGroup.controls['lastName'].value = this.detailClientResponse.lastName;
    this.updateClientFormGroup.controls['phone'].value = this.detailClientResponse.phone;
    this.updateClientFormGroup.controls['email'].value = this.detailClientResponse.email;
    this.updateClientFormGroup.controls['street'].value = this.detailClientResponse.addressView.street;
    this.updateClientFormGroup.controls['zipCode'].value = this.detailClientResponse.addressView.zipCode;
    this.updateClientFormGroup.controls['city'].value = this.detailClientResponse.addressView.city;
    this.updateClientFormGroup.controls['birthdayDate'].value = this.birthdayDate;
    this.updateClientFormGroup.controls['password'].value = this.detailClientResponse.password;
    this.updateClientFormGroup.controls['repeatPassword'].value = this.detailClientResponse.password;
    this.updateClientFormGroup.updateValueAndValidity();
  }


  submit() {

    if (this.updateClientFormGroup.valid) {
      this.detailClientResponse.birthDay = new Date(this.birthdayDate?.year, this.birthdayDate?.month - 1, this.birthdayDate?.day)
      if (this.detailClientResponse.isAdmin) this.detailClientResponse.roleView.role = 'ADMIN'
      else this.detailClientResponse.roleView.role = 'CLIENT'
      let isEqual = DetailUserMoreResponse.isEqualsDeep(this.detailClientResponse, this.detailClientResponseOrginal)

      if (!isEqual) {
        this.loading = true;
        this.isErrorActive = false;
        this.errorMsg = ''
        this.userService.updateClient(this.detailClientResponse).subscribe(
          data=>{
            this.loading = false;
            const modalRef = this.ngbModal.open(PopupInformationViewComponent);
            modalRef.componentInstance.message = data.message;
            this.router.navigate(['/', 'user-panel']);
          },error => {
            this.loading = false;
            this.isErrorActive = true;
            let errorHandler: ErrorHandler = new ErrorHandler();
            this.errorMsg = errorHandler.handle(error,this.errorMsg)
          }
        )
      } else {
        this.isErrorActive = true;
        this.errorMsg = 'Brak zmian'
      }
    }

  }


  search($event: DetailUserMoreResponse) {
    this.detailClientResponse = $event
  }

  private employeeDateValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const value = control.value;

      if (!value) {
        return null;
      }
      let birthdayC = this.updateClientFormGroup.get('birthdayDate')?.value as NgbDateStruct;

      let birthday = new Date(birthdayC.year, birthdayC.month - 1, birthdayC.day)

      let employeeDay = new Date(value.year, value.month - 1, value.day)
      const employeeDateValid = birthday.getTime() < employeeDay.getTime();


      return !employeeDateValid ? {employeeDateValid: true} : null;
    }

  }

  changeStatusOfAdminCheckbox() {
    this.detailClientResponse.isAdmin = !this.detailClientResponse.isAdmin
  }

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

      const passwordValue = this.updateClientFormGroup?.controls['password']?.value;
      if (!value) {
        return null;
      }
      const passwordValid = passwordValue == value;


      return !passwordValid ? {passwordNotSame: true} : null;
    }
  }

  activate() {
    this.userService.activateUser(this.detailClientResponse.userId).subscribe(
      data => {
        const modalRef = this.ngbModal.open(PopupInformationViewComponent);
        modalRef.componentInstance.message = data.message;
        this.router.navigate(['/', 'user-panel']);
      }, error => {
        this.isErrorActive = true;
        this.errorMsg = error.error;
      }
    )
  }

}
