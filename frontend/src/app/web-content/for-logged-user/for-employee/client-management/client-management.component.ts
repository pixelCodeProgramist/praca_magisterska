import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";

@Component({
  selector: 'app-client-management',
  templateUrl: './client-management.component.html',
  styleUrls: ['./client-management.component.css']
})
export class ClientManagementComponent implements OnInit {

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

}
