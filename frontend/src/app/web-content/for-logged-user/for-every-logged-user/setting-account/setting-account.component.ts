import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../../shared/user.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {DetailUserResponse} from "../../../../../models/detail-user/DetailUserResponse";
import {NgbDate, NgbDateStruct, NgbInputDatepicker, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  PopupInformationViewComponent
} from "../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";

@Component({
  selector: 'app-setting-account',
  templateUrl: './setting-account.component.html',
  styleUrls: ['./setting-account.component.css']
})
export class SettingAccountComponent implements OnInit {
  settingFormGroup: any;
  canToggleCalendar: boolean = false;

  today: any;
  detailUserResponse: DetailUserResponse = new DetailUserResponse()
  detailUserResponseOrginal: DetailUserResponse = new DetailUserResponse();

  isErrorActive: boolean = false;
  errorMsg: string = '';

  date!: NgbDateStruct;

  constructor(public userService: UserService,  private ngbModal: NgbModal) {
  }

  ngOnInit(): void {
    if (localStorage.getItem('type') === 'updateAccount') {
      localStorage.removeItem('type')
      const modalRef = this.ngbModal.open(PopupInformationViewComponent);
      modalRef.componentInstance.message = 'Dane zaktualizowane pomyślnie';
    }
    if (localStorage.getItem('userId') != null) {
      let userId: number = Number(localStorage.getItem('userId'));
      this.userService.getDetailUserInformation(userId).subscribe(
        data => {
          this.detailUserResponse = data;
          this.detailUserResponseOrginal = DetailUserResponse.clone(this.detailUserResponse);
          this.detailUserResponse.birthDay = new Date(this.detailUserResponse.birthDay)
          this.detailUserResponseOrginal.birthDay = new Date(this.detailUserResponseOrginal.birthDay)
          this.date = new NgbDate(
            this.detailUserResponseOrginal.birthDay.getFullYear(),
            this.detailUserResponseOrginal.birthDay.getMonth() +1,
            this.detailUserResponseOrginal.birthDay.getDate());

        }, error => {
          localStorage.clear();
          location.reload();
        }
      )
    }

    this.setAllValidators();


  }

  submit() {
    this.updateInputValues()
    this.detailUserResponse.password = this.settingFormGroup.get('password').value;
    this.detailUserResponse.birthDay = new Date(this.date.year,this.date.month-1, this.date.day)
    if(this.detailUserResponse.password === undefined) this.detailUserResponse.password = '';
    if(this.detailUserResponseOrginal.password === undefined) this.detailUserResponseOrginal.password = '';
    let equalsFields = DetailUserResponse.isEquals(this.detailUserResponse, this.detailUserResponseOrginal);
    let validForm = this.settingFormGroup.valid;
    let passwordCorrect = this.detailUserResponse.password.length>=8;


    if ((!equalsFields || passwordCorrect) && validForm) {
      this.userService.updateUser(this.detailUserResponse).subscribe(
        data=>{
          localStorage.setItem('type','updateAccount')
          location.reload()
          console.log(data)
        },error => {
          console.log(error)
        }
      )
    }


    if(equalsFields) {
      this.errorMsg = 'Pola nie zostały zmienione';
      this.isErrorActive = true;
    }

    if(!validForm) {
      this.errorMsg = 'Formularz zawiera błędy';
      this.isErrorActive = true;
    }

  }

  public checkError = (controlName: string, errorName: string) => {
    return this.settingFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.settingFormGroup!.controls[controlName]?.errors != null && this.settingFormGroup!.controls[controlName].touched;
  }


  private setAllValidators() {
    this.settingFormGroup = new FormGroup({
      firstName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      phone: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('^[0-9]*$')]),
      password: new FormControl('', [ Validators.minLength(8), this.createPasswordStrengthValidator()]),
      email: new FormControl('', [Validators.required, Validators.email]),
      street: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      zipCode: new FormControl('', [Validators.required, Validators.pattern("^\\d{2}[- ]{0,1}\\d{3}$")]),
      city: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
    });
    this.updateInputValues();


  }

  private updateInputValues() {

    this.settingFormGroup.controls['firstName'].value = this.detailUserResponse.firstName;
    this.settingFormGroup.controls['lastName'].value = this.detailUserResponse.lastName;
    this.settingFormGroup.controls['phone'].value = this.detailUserResponse.phone;
    this.settingFormGroup.controls['password'].value = this.detailUserResponse.password === undefined ? '' : this.detailUserResponse.password;
    this.settingFormGroup.controls['email'].value = this.detailUserResponse.email;
    this.settingFormGroup.controls['street'].value = this.detailUserResponse.addressView.street;
    this.settingFormGroup.controls['zipCode'].value = this.detailUserResponse.addressView.zipCode;
    this.settingFormGroup.controls['city'].value = this.detailUserResponse.addressView.city;
    this.settingFormGroup.updateValueAndValidity();
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
      this.settingFormGroup.get(controlname).clearValidators();
      this.settingFormGroup.get(controlname)?.updateValueAndValidity();
    } else {
      this.setAllValidators()

      this.detailUserResponse = DetailUserResponse.clone(this.detailUserResponse)
    }
  }
}
