import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../shared/user.service";

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css']
})
export class UserPanelComponent implements OnInit {

  constructor(public userService: UserService) {

  }

  ngOnInit() {

  }


}
