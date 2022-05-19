import { Component, OnInit } from '@angular/core';
import {PasswordChangerRequest} from "../../../../../../models/forget-password-package/request/PasswordChangerRequest";
import {ActivatedRoute, ActivatedRouteSnapshot} from "@angular/router";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {UserService} from "../../../../../../shared/user.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PopupInformationViewComponent} from "../../popup-information-view/popup-information-view.component";
import {ErrorHandler} from "../../../../../../shared/ErrorHandler";

@Component({
  selector: 'app-forget-password-response',
  templateUrl: './forget-password-response.component.html',
  styleUrls: ['./forget-password-response.component.css']
})
export class ForgetPasswordResponseComponent implements OnInit {
  isErrorActive: any;
  errorMsg: any;
  passwordChangerRequest: PasswordChangerRequest = new PasswordChangerRequest();
  public passwordChangerFormGroup!: FormGroup;
  loading: boolean = false;
  constructor(private userService: UserService,  private ngbModal: NgbModal, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.passwordChangerRequest.token = this.route.snapshot.queryParams['token'];


    this.passwordChangerFormGroup = new FormGroup({
      password : new FormControl('', [Validators.required, Validators.minLength(8), this.createPasswordStrengthValidator()]),
      repeatPassword : new FormControl('', [Validators.required, this.createPasswordSameValidator()]),
    });
  }

  submit() {
    if(this.passwordChangerFormGroup.valid) {
      this.loading = true;
      this.userService.changePassword(this.passwordChangerRequest).subscribe(
        data=>{
          this.loading = false;
          const modalRef = this.ngbModal.open(PopupInformationViewComponent);
          modalRef.componentInstance.message = 'Hasło zmieniono pomyślnie';
          this.passwordChangerFormGroup.reset();
        },error => {
          this.loading = false;
          let errorHandler: ErrorHandler = new ErrorHandler();
          this.errorMsg = errorHandler.handle(error,this.errorMsg)
          this.isErrorActive = true;
        }
      )
    }
  }

  private createPasswordStrengthValidator(): ValidatorFn {
    return (control:AbstractControl) : ValidationErrors | null => {

      const value = control.value;

      if (!value) {
        return null;
      }

      const hasUpperCase = /[A-Z]+/.test(value);

      const hasLowerCase = /[a-z]+/.test(value);

      const hasNumeric = /[0-9]+/.test(value);

      const passwordValid = hasUpperCase && hasLowerCase && hasNumeric;

      return !passwordValid ? {passwordStrength:true}: null;
    }
  }

  private createPasswordSameValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const value = control.value;

      const passwordValue = this.passwordChangerFormGroup?.controls['password']?.value;
      if (!value) {
        return null;
      }
      const passwordValid = passwordValue == value;


      return !passwordValid ? {passwordNotSame: true} : null;
    }
  }

  public hasError = (controlName: string) => {
    return this.passwordChangerFormGroup!.controls[controlName]?.errors!=null && this.passwordChangerFormGroup!.controls[controlName].touched;
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.passwordChangerFormGroup.controls[controlName].hasError(errorName);
  }

}
