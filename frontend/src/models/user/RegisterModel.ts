export class RegisterModel {
  firstName: string;
  lastName: string;
  phone: number;
  email: string;
  password: string;
  birthday: Date
  street: string;
  houseNr: string;
  zipCode: string;
  city: string;


  constructor(firstName: string, lastName: string, phone: number, email: string, password: string, birthday: Date, street: string, houseNr: string, zipCode: string, city: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.password = password;
    this.birthday = birthday;
    this.street = street;
    this.houseNr = houseNr;
    this.zipCode = zipCode;
    this.city = city;

  }
}
