export interface GeneralInformationResponse {
  generalInformationId: number;
  email: string;
  hourFrom: string;
  hourTo: string;
  branches: Branch[];
}

export interface Branch {
  branchId: number;
  street: string;
  zipCode: string;
  city: string;
  latitude: number;
  longitude: number;
  phone: string;
}
