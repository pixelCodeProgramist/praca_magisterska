import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../../shared/user.service";
import {LoginModel} from "../../../../../models/user/LoginModel";
import {LoginResponseModel} from "../../../../../models/user/LoginResponseModel";

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

  constructor(private userService: UserService) { }

  ngOnInit(): void {

  }

  submit() {
    if(this.loginRequest.isNotEmpty()) {
      this.userService.login(this.loginRequest).subscribe(
        data=>{
          this.authenticationResponse = data;
          localStorage.setItem('userId', this.authenticationResponse.userId.toString());
          localStorage.setItem('name', this.authenticationResponse.name);
          localStorage.setItem('role', this.authenticationResponse.role);
          localStorage.setItem('token', this.authenticationResponse.token);
          location.assign('/mainSite');
      },error => {

        })
    } else {
      this.errorMsg = 'Login or password cannot be empty!'
    }
  }
}
