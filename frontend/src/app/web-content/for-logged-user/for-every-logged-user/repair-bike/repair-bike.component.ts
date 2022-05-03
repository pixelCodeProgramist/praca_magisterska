import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";

@Component({
  selector: 'app-repair-bike',
  templateUrl: './repair-bike.component.html',
  styleUrls: ['./repair-bike.component.css']
})
export class RepairBikeComponent implements OnInit {

  constructor(public userService: UserService) { }

  ngOnInit(): void {
  }

}
