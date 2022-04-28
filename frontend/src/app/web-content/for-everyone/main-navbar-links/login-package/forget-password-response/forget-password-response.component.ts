import { Component, OnInit } from '@angular/core';
import {PasswordChangerRequest} from "../../../../../../models/forget-password-package/request/PasswordChangerRequest";

@Component({
  selector: 'app-forget-password-response',
  templateUrl: './forget-password-response.component.html',
  styleUrls: ['./forget-password-response.component.css']
})
export class ForgetPasswordResponseComponent implements OnInit {
  isErrorActive: any;
  errorMsg: any;
  passwordChangerRequest: PasswordChangerRequest = new PasswordChangerRequest();

  constructor() { }

  ngOnInit(): void {
  }

  submit() {

  }
}
