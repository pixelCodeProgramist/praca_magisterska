import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalService} from "./global.service";
import {RegisterModel} from "../models/user/RegisterModel";
import {StringMessageModelResponse} from "../models/StringMessageModelResponse";
import {LoginModel} from "../models/user/LoginModel";
import {LoginResponseModel} from "../models/user/LoginResponseModel";
import {ForgetPasswordRequest} from "../models/forget-password-package/request/ForgetPasswordRequest";
import {PasswordChangerRequest} from "../models/forget-password-package/request/PasswordChangerRequest";
import {DetailUserResponse} from "../models/detail-user/DetailUserResponse";
import {DetailEmployeeResponse} from "../models/detail-user/DetailEmployeeResponse";
import {BackendResponse} from "../models/BackendResponse";

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private REGISTER_URL = `${GlobalService.BASE_URL}/user/register`;
  private LOGIN_URL = `${GlobalService.BASE_URL}/auth/user/login`;
  private LOGOUT_URL = `${GlobalService.BASE_URL}/auth/user/logout`;
  private FORGET_PASSWORD_URL = `${GlobalService.BASE_URL}/mail/forgetPassword`;
  private CHANGE_PASSWORD_URL = `${GlobalService.BASE_URL}/user/changePassword`;
  private DETAIL_USER_URL = `${GlobalService.BASE_URL}/user/detailUser`;
  private UPDATE_DETAIL_USER_URL = `${GlobalService.BASE_URL}/user/update`;
  private ADD_EMPLOYEE_URL = `${GlobalService.BASE_URL}/user/employee/add`;
  private ALL_EMPLOYEE_URL = `${GlobalService.BASE_URL}/user/employee/all`;
  private REMOVE_EMPLOYEE_URL = `${GlobalService.BASE_URL}/user/employee/remove`;
  private UPDATE_EMPLOYEE_URL = `${GlobalService.BASE_URL}/user/employee/update`;

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

  getDetailUserInformation(id: number) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.get<DetailUserResponse>(this.DETAIL_USER_URL+'?id='+id, {headers})
  }

  updateUser(detailUser: DetailUserResponse) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.patch<StringMessageModelResponse>(this.UPDATE_DETAIL_USER_URL, detailUser,{headers})
  }

  getRole() {
    return localStorage.getItem('role');
  }

  addEmployee(detailEmployeeResponse: DetailEmployeeResponse) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.post<BackendResponse>(this.ADD_EMPLOYEE_URL, detailEmployeeResponse, {headers})
  }

  getAllEmployee() {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.get<DetailEmployeeResponse[]>(this.ALL_EMPLOYEE_URL, {headers})
  }

  removeUser(id: number) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.patch<BackendResponse>(this.REMOVE_EMPLOYEE_URL+'/'+id, null,{headers})
  }


  updateEmployee(detailEmployeeResponse: DetailEmployeeResponse) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.put<BackendResponse>(this.UPDATE_EMPLOYEE_URL, detailEmployeeResponse, {headers})
  }
}

