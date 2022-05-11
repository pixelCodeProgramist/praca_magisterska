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
import {DetailUserMoreResponse} from "../models/detail-user/DetailUserMoreResponse";
import {BackendResponse} from "../models/BackendResponse";
import {Role} from "./enum/Role";

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
  private ALL_ACTIVE_CLIENTS_URL = `${GlobalService.BASE_URL}/user/client/active/all`;
  private REMOVE_CLIENT_URL = `${GlobalService.BASE_URL}/user/client/remove`;
  private ALL_CLIENTS_URL = `${GlobalService.BASE_URL}/user/client/all`;
  private ACTIVATE_CLIENT_URL = `${GlobalService.BASE_URL}/user/client/activate`;
  private UPDATE_CLIENT_URL = `${GlobalService.BASE_URL}/user/client/update`;

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

  addEmployee(detailEmployeeResponse: DetailUserMoreResponse) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.post<BackendResponse>(this.ADD_EMPLOYEE_URL, detailEmployeeResponse, {headers})
  }

  getAllEmployee() {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.get<DetailUserMoreResponse[]>(this.ALL_EMPLOYEE_URL, {headers})
  }

  removeUser(id: number, role: Role) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    if(role == Role.EMPLOYEE) return this.http.patch<BackendResponse>(this.REMOVE_EMPLOYEE_URL+'/'+id, null,{headers})
    if(role == Role.CLIENT) return this.http.patch<BackendResponse>(this.REMOVE_CLIENT_URL+'/'+id, null,{headers})
    return null;
  }


  updateEmployee(detailEmployeeResponse: DetailUserMoreResponse) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.put<BackendResponse>(this.UPDATE_EMPLOYEE_URL, detailEmployeeResponse, {headers})
  }

  getAllClients(active: boolean = false) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    if(active) return this.http.get<DetailUserMoreResponse[]>(this.ALL_ACTIVE_CLIENTS_URL, {headers})
    return this.http.get<DetailUserMoreResponse[]>(this.ALL_CLIENTS_URL, {headers})
  }

  activateUser(userId: number) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.get<BackendResponse>(this.ACTIVATE_CLIENT_URL+'/'+userId, {headers})
  }

  updateClient(detailClientResponse: DetailUserMoreResponse) {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + localStorage.getItem('token') });
    return this.http.put<BackendResponse>(this.UPDATE_CLIENT_URL, detailClientResponse, {headers})
  }

  isAdmin() {
    if(this.getRole() === "ADMIN") return true;
    return false;
  }
}

