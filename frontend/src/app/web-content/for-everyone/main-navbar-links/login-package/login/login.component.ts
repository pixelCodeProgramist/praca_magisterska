import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../../../shared/user.service";
import {LoginModel} from "../../../../../../models/user/LoginModel";
import {LoginResponseModel} from "../../../../../../models/user/LoginResponseModel";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PopupInformationViewComponent} from "../../popup-information-view/popup-information-view.component";
import {ErrorHandler} from "../../../../../../shared/ErrorHandler";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isErrorActive: boolean = false;
  loginRequest: LoginModel = new LoginModel()
  errorMsg: string = '';
  authenticationResponse: LoginResponseModel = new LoginResponseModel()
  loading: boolean = false;

  constructor(private userService: UserService, private ngbModal: NgbModal) { }

  ngOnInit(): void {
    if(localStorage.getItem('type')==='forgetPasswordRequest') {
      localStorage.removeItem('type');
      const modalRef = this.ngbModal.open(PopupInformationViewComponent);
      modalRef.componentInstance.message = 'Wysłano prośbę p zmianę hasłą pomyślnie. Proszę sprawdź swoją slrzynkę mailową.';
    }
  }

  submit() {
    if(this.loginRequest.isNotEmpty()) {
      this.loading = true;
      this.userService.login(this.loginRequest).subscribe(
        data=>{
          this.loading = false;
          this.authenticationResponse = data;
          localStorage.setItem('userId', this.authenticationResponse.userId.toString());
          localStorage.setItem('name', this.authenticationResponse.name);
          localStorage.setItem('role', this.authenticationResponse.role);
          localStorage.setItem('token', this.authenticationResponse.token);
          location.assign('/mainSite');
      },error => {
          this.loading = false;
          this.isErrorActive = true
          let errorHandler: ErrorHandler = new ErrorHandler();
          this.errorMsg = errorHandler.handle(error,this.errorMsg)
        })
    } else {
      this.isErrorActive = true
      this.errorMsg = 'Login or password cannot be empty!'
    }
  }
}
