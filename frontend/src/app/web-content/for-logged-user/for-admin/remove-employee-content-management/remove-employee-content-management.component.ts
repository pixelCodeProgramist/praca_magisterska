import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DetailUserMoreResponse} from "../../../../../models/detail-user/DetailUserMoreResponse";
import {UserService} from "../../../../../shared/user.service";
import {
  PopupInformationViewComponent
} from "../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";
import {Router} from "@angular/router";
import {Role} from "../../../../../shared/enum/Role";

@Component({
  selector: 'app-remove-employee-content-management',
  templateUrl: './remove-employee-content-management.component.html',
  styleUrls: ['./remove-employee-content-management.component.css']
})
export class RemoveEmployeeContentManagementComponent implements OnInit {

  keyword = 'firstName';

  phone!: number;
  password!: string;

  detailUserResponse: DetailUserMoreResponse = new DetailUserMoreResponse()
  detailUserResponses: DetailUserMoreResponse[] = []

  isErrorActive: boolean = false;
  errorMsg: string = '';
  branchInfo!: string;


  constructor(private userService: UserService, private ngbModal: NgbModal, private router: Router) { }



  ngOnInit(): void {
    this.userService.getAllEmployee().subscribe(
      data=>{
        this.detailUserResponses = data;
        this.detailUserResponse = this.detailUserResponses[0];
        this.setRoleCheckBox();
        this.detailUserResponse.birthDay = new Date(this.detailUserResponse.birthDay);
        this.detailUserResponse.employeeDate = new Date(this.detailUserResponse.employeeDate);
        this.branchInfo = this.detailUserResponse.branchView.street + ' ' + this.detailUserResponse.branchView.city
      },error => {
        localStorage.clear()
        this.router.navigate(['/', 'login']);
      }
    )
  }

  setRoleCheckBox() {
    if(this.detailUserResponse.roleView.role == 'ADMIN') this.detailUserResponse.isAdmin = true;
    else this.detailUserResponse.isAdmin = false;
  }





  submit() {

    this.userService.removeUser(this.detailUserResponse.userId, Role.EMPLOYEE)?.subscribe(
      data=>{
        const modalRef = this.ngbModal.open(PopupInformationViewComponent);
        modalRef.componentInstance.message = data.message;
        this.router.navigate(['/', 'user-panel']);
      },error => {
        this.isErrorActive = true;
        this.errorMsg = error.error;
      }
    )
  }

  search($event: DetailUserMoreResponse) {
    this.detailUserResponse = $event
  }


}
