import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../../shared/user.service";

@Component({
  selector: 'app-client-management',
  templateUrl: './client-management.component.html',
  styleUrls: ['./client-management.component.css']
})
export class ClientManagementComponent implements OnInit {

  currentClientNav: string = ''

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

  setActive(employeeElement: HTMLAnchorElement, employee: string) {
    this.removeActiveClass();
    employeeElement.classList.add('active')
    this.currentClientNav = employee
  }

  private removeActiveClass() {
    let liList: HTMLCollection | undefined = document.getElementById('employeeUl')?.getElementsByTagName('a');
    for (var i = 0; i < liList!.length; i++) {
      // @ts-ignore
      liList[i].classList.remove('active')
    }
  }
}
