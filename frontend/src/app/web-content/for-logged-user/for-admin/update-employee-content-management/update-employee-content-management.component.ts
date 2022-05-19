import {Component, OnInit} from '@angular/core';
import {DetailUserMoreResponse} from "../../../../../models/detail-user/DetailUserMoreResponse";
import {UserService} from "../../../../../shared/user.service";
import {NgbDate, NgbDateStruct, NgbInputDatepicker, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {Branch} from "../../../../../models/general-information/response/GeneralInformationResponse";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {
  PopupInformationViewComponent
} from "../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";
import {ErrorHandler} from "../../../../../shared/ErrorHandler";

@Component({
  selector: 'app-update-employee-content-management',
  templateUrl: './update-employee-content-management.component.html',
  styleUrls: ['./update-employee-content-management.component.css']
})
export class UpdateEmployeeContentManagementComponent implements OnInit {

  keyword = 'firstName';

  phone!: number;
  repeatPassword!: string;

  detailEmployeeResponse: DetailUserMoreResponse = new DetailUserMoreResponse()
  detailEmployeeResponses: DetailUserMoreResponse[] = []

  isErrorActive: boolean = false;
  errorMsg: string = '';
  branchInfo!: string;

  updateEmployeeFormGroup: any;
  canToggleCalendar: boolean = false;

  today: any;

  isSelectPossible: boolean = false;

  detailEmployeeResponseOrginal: DetailUserMoreResponse = new DetailUserMoreResponse();

  birthdayDate!: NgbDateStruct;

  employmentDate!: NgbDateStruct;

  branches: Branch[] = [];
  selectedBranchOption!: string;
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

    this.userService.getAllEmployee().subscribe(
      data => {
        this.loading = false;
        this.detailEmployeeResponses = data;
        this.detailEmployeeResponse = this.detailEmployeeResponses[0];
        this.selectedBranchOption = this.detailEmployeeResponse?.branchView?.street + ' ' + this.detailEmployeeResponse?.branchView?.city

        this.setRoleCheckBox();

        this.detailEmployeeResponse.birthDay = new Date(this.detailEmployeeResponse.birthDay);
        this.detailEmployeeResponse.employeeDate = new Date(this.detailEmployeeResponse.employeeDate);
        this.detailEmployeeResponseOrginal = DetailUserMoreResponse.cloneDeep(this.detailEmployeeResponse)

        this.birthdayDate = new NgbDate(
          this.detailEmployeeResponseOrginal.birthDay.getFullYear(),
          this.detailEmployeeResponseOrginal.birthDay.getMonth() + 1,
          this.detailEmployeeResponseOrginal.birthDay.getDate());

        this.employmentDate = new NgbDate(
          this.detailEmployeeResponseOrginal.employeeDate.getFullYear(),
          this.detailEmployeeResponseOrginal.employeeDate.getMonth() + 1,
          this.detailEmployeeResponseOrginal.employeeDate.getDate());

        this.branchInfo = this.detailEmployeeResponse.branchView.street + ' ' + this.detailEmployeeResponse.branchView.city
      }, error => {
        this.loading = false;
        localStorage.clear()
        this.router.navigate(['/', 'login']);
      }
    )

    this.setAllValidators();
  }

  setRoleCheckBox() {
    if (this.detailEmployeeResponse.roleView.role == 'ADMIN') this.detailEmployeeResponse.isAdmin = true;
    else this.detailEmployeeResponse.isAdmin = false;
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
      this.updateEmployeeFormGroup.get(controlname).clearValidators();
      this.updateEmployeeFormGroup.get(controlname)?.updateValueAndValidity();
    } else {
      this.setAllValidators()
      this.detailEmployeeResponse = {...this.detailEmployeeResponse};
    }

  }

  public checkError = (controlName: string, errorName: string) => {
    return this.updateEmployeeFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.updateEmployeeFormGroup!.controls[controlName]?.errors != null && this.updateEmployeeFormGroup!.controls[controlName].touched;
  }


  private setAllValidators() {
    this.updateEmployeeFormGroup = new FormGroup({
      firstName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      phone: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('^[0-9]*$')]),
      email: new FormControl('', [Validators.required, Validators.email]),
      street: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      zipCode: new FormControl('', [Validators.required, Validators.pattern("^\\d{2}[- ]{0,1}\\d{3}$")]),
      city: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      branch: new FormControl('', [Validators.required]),
      birthdayDate: new FormControl('', [Validators.required]),
      employeeDate: new FormControl('', [Validators.required, this.employeeDateValidator()]),
      password: new FormControl('', [Validators.minLength(8), this.createPasswordStrengthValidator()]),
      repeatPassword: new FormControl('', [this.createPasswordSameValidator()]),
    });
    this.updateInputValues();


  }

  private updateInputValues() {

    this.updateEmployeeFormGroup.controls['firstName'].value = this.detailEmployeeResponse.firstName;
    this.updateEmployeeFormGroup.controls['lastName'].value = this.detailEmployeeResponse.lastName;
    this.updateEmployeeFormGroup.controls['phone'].value = this.detailEmployeeResponse.phone;
    this.updateEmployeeFormGroup.controls['email'].value = this.detailEmployeeResponse.email;
    this.updateEmployeeFormGroup.controls['street'].value = this.detailEmployeeResponse.addressView.street;
    this.updateEmployeeFormGroup.controls['zipCode'].value = this.detailEmployeeResponse.addressView.zipCode;
    this.updateEmployeeFormGroup.controls['city'].value = this.detailEmployeeResponse.addressView.city;
    this.updateEmployeeFormGroup.controls['birthdayDate'].value = this.birthdayDate;
    this.updateEmployeeFormGroup.controls['employeeDate'].value = this.employmentDate;
    this.updateEmployeeFormGroup.controls['password'].value = this.detailEmployeeResponse.password;
    this.updateEmployeeFormGroup.controls['repeatPassword'].value = this.detailEmployeeResponse.password;
    this.updateEmployeeFormGroup.updateValueAndValidity();
  }


  submit() {

    if (this.updateEmployeeFormGroup.valid) {

      this.detailEmployeeResponse.birthDay = new Date(this.birthdayDate?.year, this.birthdayDate?.month - 1, this.birthdayDate?.day)
      this.detailEmployeeResponse.employeeDate = new Date(this.employmentDate?.year, this.employmentDate?.month - 1, this.employmentDate?.day)
      let branch = this.branches.filter(branch => (branch.street + ' ' + branch.city) == this.selectedBranchOption)[0];
      branch.branchId = this.detailEmployeeResponse.branchView.branchId;
      this.detailEmployeeResponse.branchView = branch;
      if (this.detailEmployeeResponse.isAdmin) this.detailEmployeeResponse.roleView.role = 'ADMIN'
      else this.detailEmployeeResponse.roleView.role = 'EMPLOYEE'
      let isEqual = DetailUserMoreResponse.isEqualsDeep(this.detailEmployeeResponse, this.detailEmployeeResponseOrginal)
      if (!isEqual) {
        this.loading = true;
        this.isErrorActive = false;
        this.errorMsg = ''
        this.userService.updateEmployee(this.detailEmployeeResponse).subscribe(
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
    this.detailEmployeeResponse = $event
  }


  setEditPossibilityForSelect(branchSelect: HTMLSelectElement) {
    this.isSelectPossible = !this.isSelectPossible;
    if (!this.isSelectPossible) branchSelect.style.background = '#e9ecef'
    else branchSelect.style.background = ''
  }

  private employeeDateValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const value = control.value;

      if (!value) {
        return null;
      }
      let birthdayC = this.updateEmployeeFormGroup.get('birthdayDate')?.value as NgbDateStruct;

      let birthday = new Date(birthdayC.year, birthdayC.month - 1, birthdayC.day)

      let employeeDay = new Date(value.year, value.month - 1, value.day)
      const employeeDateValid = birthday.getTime() < employeeDay.getTime();


      return !employeeDateValid ? {employeeDateValid: true} : null;
    }

  }

  changeStatusOfAdminCheckbox() {
    this.detailEmployeeResponse.isAdmin = !this.detailEmployeeResponse.isAdmin
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

      const passwordValue = this.updateEmployeeFormGroup?.controls['password']?.value;
      if (!value) {
        return null;
      }
      const passwordValid = passwordValue == value;


      return !passwordValid ? {passwordNotSame: true} : null;
    }
  }


}
