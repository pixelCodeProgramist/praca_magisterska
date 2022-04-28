import { Component, OnInit } from '@angular/core';
import {ForgetPasswordRequest} from "../../../../../../models/forget-password-package/request/ForgetPasswordRequest";
import {UserService} from "../../../../../../shared/user.service";

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {
  forgetPasswordRequest: ForgetPasswordRequest = new ForgetPasswordRequest();
  isErrorActive!: boolean;
  errorMsg: string = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  submit() {

  }
}
