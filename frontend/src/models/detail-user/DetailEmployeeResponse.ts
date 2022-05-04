import {AddressView, DetailUserResponse} from "./DetailUserResponse";
import {Branch} from "../general-information/response/GeneralInformationResponse";

export class DetailEmployeeResponse extends DetailUserResponse{
  userId!: number;
  isAdmin: boolean = false;
  branchId!: number;
  employeeDate!: Date;
  roleView!: Role;
  branchView!: Branch

  public static cloneDeep(o1: DetailEmployeeResponse) {
    let response: DetailEmployeeResponse = new DetailEmployeeResponse();
    response.email = o1.email;
    response.firstName = o1.firstName;
    response.lastName = o1.lastName;
    response.phone = o1.phone;
    response.birthDay = o1.birthDay;
    response.employeeDate = o1.employeeDate;
    response.password = o1.password;
    response.isAdmin = o1.isAdmin;

    response.addressView = {...o1.addressView};
    response.roleView= {...o1.roleView};
    response.branchView = {...o1.branchView};


    return response;
  }

  public static isEqualsDeep(o1: DetailEmployeeResponse, o2: DetailEmployeeResponse): boolean {
    if (AddressView.isEqual(o1.addressView, o2.addressView) &&
      o1.email == o2.email &&
      o1.firstName == o2.firstName &&
      o1.lastName == o2.lastName &&
      o1.phone == o2.phone &&
      o1.birthDay.getTime() == o2.birthDay.getTime() &&
      o1.employeeDate.getTime() == o2.employeeDate.getTime() &&
      o1.password == o2.password &&
      o1.isAdmin == o2.isAdmin &&
      o1.branchView.branchId == o2.branchView.branchId
    ) return true;
    return false;
  }
}

export class Role {
  id!: number;
  role!: string;
}


