import {Component, OnInit} from '@angular/core';
import {DetailUserMoreResponse} from "../../../../../../models/detail-user/DetailUserMoreResponse";
import {UserService} from "../../../../../../shared/user.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {
  PopupInformationViewComponent
} from "../../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";
import {Role} from "../../../../../../shared/enum/Role";

@Component({
  selector: 'app-remove-client-content-management',
  templateUrl: './remove-client-content-management.component.html',
  styleUrls: ['./remove-client-content-management.component.css']
})
export class RemoveClientContentManagementComponent implements OnInit {

  keyword = 'firstName';

  phone!: number;
  password!: string;

  detailUserResponse: DetailUserMoreResponse = new DetailUserMoreResponse()
  detailUserResponses: DetailUserMoreResponse[] = []

  isErrorActive: boolean = false;
  errorMsg: string = '';


  constructor(private userService: UserService, private ngbModal: NgbModal, private router: Router) {
  }


  ngOnInit(): void {
    this.userService.getAllClients(true).subscribe(
      data => {
        this.detailUserResponses = data;
        this.detailUserResponse = this.detailUserResponses[0];
        if(this.detailUserResponse!=undefined) {
          this.setRoleCheckBox();
          this.detailUserResponse.birthDay = new Date(this.detailUserResponse.birthDay);
        }else {
          const modalRef = this.ngbModal.open(PopupInformationViewComponent);
          modalRef.componentInstance.message = 'W systemie brak aktywnych klientów';
        }
      }, error => {
        localStorage.clear()
        this.router.navigate(['/', 'login']);
      }
    )
  }

  setRoleCheckBox() {

    if (this.detailUserResponse.roleView.role == 'ADMIN') this.detailUserResponse.isAdmin = true;
    else this.detailUserResponse.isAdmin = false;


  }


  submit() {

    this.userService.removeUser(this.detailUserResponse.userId, Role.CLIENT)?.subscribe(
      data => {
        const modalRef = this.ngbModal.open(PopupInformationViewComponent);
        modalRef.componentInstance.message = data.message;
        this.router.navigate(['/', 'user-panel']);
      }, error => {
        this.isErrorActive = true;
        this.errorMsg = error.error;
      }
    )
  }

  search($event: DetailUserMoreResponse) {
    this.detailUserResponse = $event
  }

}
