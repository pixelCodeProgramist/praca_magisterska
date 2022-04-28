import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {RegisterModel} from "../models/user/RegisterModel";
import {StringMessageModelResponse} from "../models/StringMessageModelResponse";
import {LoginModel} from "../models/user/LoginModel";
import {LoginResponseModel} from "../models/user/LoginResponseModel";
import {ForgetPasswordRequest} from "../models/forget-password-package/request/ForgetPasswordRequest";
import {PasswordChangerRequest} from "../models/forget-password-package/request/PasswordChangerRequest";
import {ActivatedRouteSnapshot} from "@angular/router";

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private REGISTER_URL = `${GlobalService.BASE_URL}/user/register`;
  private LOGIN_URL = `${GlobalService.BASE_URL}/auth/user/login`;
  private LOGOUT_URL = `${GlobalService.BASE_URL}/auth/user/logout`;
  private FORGET_PASSWORD_URL = `${GlobalService.BASE_URL}/mail/forgetPassword`;
  private CHANGE_PASSWORD_URL = `${GlobalService.BASE_URL}/user/changePassword`;

  constructor(private http: HttpClient) {
  }

  register(registerModel: RegisterModel) {
    return this.http.post<StringMessageModelResponse>(this.REGISTER_URL, registerModel);
  }

  login(loginModel: LoginModel) {
    return this.http.post<LoginResponseModel>(this.LOGIN_URL, loginModel);
  }

  logout() {

    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });

    return this.http.get(this.LOGOUT_URL, {headers});
  }

  sendForgetPasswordRequest(forgetPasswordRequest: ForgetPasswordRequest) {
    return this.http.post(this.FORGET_PASSWORD_URL, forgetPasswordRequest)
  }

  isLoggedIn() {
    if (localStorage.getItem('token') !== null)
      return true;
    else
      return false;
  }

  changePassword(passwordChangerRequest: PasswordChangerRequest) {

    return this.http.post(this.CHANGE_PASSWORD_URL, passwordChangerRequest)
  }
}

