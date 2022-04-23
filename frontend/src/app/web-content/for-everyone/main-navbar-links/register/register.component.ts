import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {UserService} from "../../../../../shared/user.service";
import {RegisterModel} from "../../../../../models/user/RegisterModel";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PopupInformationViewComponent} from "../popup-information-view/popup-information-view.component";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerFormGroup!: FormGroup;

  constructor(private userService: UserService, private ngbModal: NgbModal) { }

  ngOnInit(): void {
    this.registerFormGroup = new FormGroup({
      firstName : new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      lastName : new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      phone : new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('^[0-9]*$')]),
      password : new FormControl('', [Validators.required, Validators.minLength(8), this.createPasswordStrengthValidator()]),
      repeatPassword : new FormControl('', [Validators.required, this.createPasswordSameValidator()]),
      email : new FormControl('', [Validators.required, Validators.email])
    });

  }

  public checkError = (controlName: string, errorName: string) => {
    return this.registerFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.registerFormGroup!.controls[controlName]?.errors!=null && this.registerFormGroup!.controls[controlName].touched;
  }
  isRulesAcceptedCheckbox: boolean = false;

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

      const passwordValue = this.registerFormGroup?.controls['password']?.value;
      if (!value) {
        return null;
      }
      const passwordValid = passwordValue == value;


      return !passwordValid ? {passwordNotSame: true} : null;
    }
  }

  submit() {
    if(this.registerFormGroup.valid&&this.isRulesAcceptedCheckbox) {
      let registerModel: RegisterModel = new RegisterModel(
        this.registerFormGroup.controls['firstName']?.value,
        this.registerFormGroup.controls['lastName']?.value,
        this.registerFormGroup.controls['phone']?.value,
        this.registerFormGroup.controls['email']?.value,
        this.registerFormGroup.controls['password']?.value
      )
      this.userService.register(registerModel).subscribe(
        data=>{
          const modalRef = this.ngbModal.open(PopupInformationViewComponent);
          modalRef.componentInstance.message = 'Zarejestrowano pomyślnie';
          this.isRulesAcceptedCheckbox = false;
          this.registerFormGroup.reset();
        },error => {
          console.log(error)
        }
      )
    } else {
      this.registerFormGroup.markAllAsTouched();
    }
  }

  changeStatusOfRulesCheckbox() {
    this.isRulesAcceptedCheckbox = !this.isRulesAcceptedCheckbox;
  }
}
