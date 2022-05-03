import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

}
