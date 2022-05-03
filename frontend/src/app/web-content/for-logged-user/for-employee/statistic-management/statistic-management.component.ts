import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";

@Component({
  selector: 'app-statistic-management',
  templateUrl: './statistic-management.component.html',
  styleUrls: ['./statistic-management.component.css']
})
export class StatisticManagementComponent implements OnInit {

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

}
