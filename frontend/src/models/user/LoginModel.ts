export class LoginModel {
  login: string = '';
  password: string = '';
  isNotEmpty() {
    if(this.login.trim().length>0&&this.password.trim().length>0) return true;
    return false;
  }
}
