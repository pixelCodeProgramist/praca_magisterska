export class DetailUserResponse {
  addressView: AddressView = new AddressView();
  email: string = '';
  firstName: string = '';
  lastName: string = '';
  prevEmail: string = '';
  phone: number = 0;
  birthDay: Date = new Date();
  password: string = '';

  public static clone(o1: DetailUserResponse) {
    let response: DetailUserResponse = new DetailUserResponse();
    response.email = o1.email;
    response.firstName = o1.firstName;
    response.lastName = o1.lastName;
    response.phone = o1.phone;
    response.birthDay = o1.birthDay;
    response.password = o1.password;
    response.addressView.zipCode = o1.addressView.zipCode;
    response.addressView.city = o1.addressView.city;
    response.addressView.street = o1.addressView.street;
    response.addressView.houseNr = o1.addressView.houseNr;
    return response;
  }



  public static isEquals(o1: DetailUserResponse, o2: DetailUserResponse): boolean {


    if (AddressView.isEqual(o1.addressView, o2.addressView) &&
      o1.email == o2.email &&
      o1.firstName == o2.firstName &&
      o1.lastName == o2.lastName &&
      o1.phone == o2.phone &&
      o1.birthDay.getTime() == o2.birthDay.getTime() &&
      o1.password == o2.password
    ) return true;
    return false;
  }


}

export class AddressView {
  city: string = '';
  houseNr: string = '';
  street: string = '';
  zipCode: string = '';

  static isEqual(o1: AddressView, o2: AddressView): boolean {
    if (
      o1.city == o2.city && o1.houseNr == o2.houseNr && o1.street == o2.street && o1.zipCode == o2.zipCode
    ) return true;
    return false;
  }

}
