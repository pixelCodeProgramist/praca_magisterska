import { Component, OnInit } from '@angular/core';
import {ForgetPasswordRequest} from "../../../../../../models/forget-password-package/request/ForgetPasswordRequest";
import {UserService} from "../../../../../../shared/user.service";
import {PopupInformationViewComponent} from "../../popup-information-view/popup-information-view.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ErrorHandler} from "../../../../../../shared/ErrorHandler";

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {
  forgetPasswordRequest: ForgetPasswordRequest = new ForgetPasswordRequest();
  isErrorActive!: boolean;
  errorMsg: string = '';
  loading: boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  submit() {
    this.loading = true;
    if(this.forgetPasswordRequest?.mail.trim() != '') {
      this.userService.sendForgetPasswordRequest(this.forgetPasswordRequest).subscribe(
        data=>{
          this.loading = false;
          localStorage.setItem('type','forgetPasswordRequest')
          location.assign('/login');
        },error => {
          this.loading = false;
          this.isErrorActive = true;
          let errorHandler: ErrorHandler = new ErrorHandler();
          this.errorMsg = errorHandler.handle(error,this.errorMsg)

        }
      )
    }
  }
}
