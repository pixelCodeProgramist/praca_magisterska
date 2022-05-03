import {DetailUserResponse} from "./DetailUserResponse";

export class DetailEmployeeResponse extends DetailUserResponse{
  isAdmin: boolean = false;
  branchId!: number;
  employeeDate!: Date;
}
