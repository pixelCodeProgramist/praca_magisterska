export class RegisterModel {
  firstName: string;
  lastName: string;
  phone: number;
  email: string;
  password: string;


  constructor(firstName: string, lastName: string, phone: number, email: string, password: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.password = password;
  }
}
