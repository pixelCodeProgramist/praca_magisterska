import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";

@Component({
  selector: 'app-employee-management',
  templateUrl: './employee-management.component.html',
  styleUrls: ['./employee-management.component.css']
})
export class EmployeeManagementComponent implements OnInit {
 currentEmployeeNav: string = ''

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

  setActive(employeeElement: HTMLAnchorElement, employee: string) {
    this.removeActiveClass();
    employeeElement.classList.add('active')
    this.currentEmployeeNav = employee
  }

  private removeActiveClass() {
    let liList: HTMLCollection | undefined = document.getElementById('employeeUl')?.getElementsByTagName('a');
    for (var i = 0; i < liList!.length; i++) {
      // @ts-ignore
      liList[i].classList.remove('active')
    }
  }
}
