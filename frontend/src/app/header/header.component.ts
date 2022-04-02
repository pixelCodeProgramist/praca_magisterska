import { Component, OnInit } from '@angular/core';
import {GeneralInformationService} from "../../shared/general-information.service";
import {GeneralInformationResponse} from "../../models/general-information/response/GeneralInformationResponse";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isCollapsed = true;
  phones: string[] = [];

  constructor(private generalInformationService: GeneralInformationService) { }

  ngOnInit(): void {
    this.generalInformationService.getGeneralInfo().subscribe(
      data=>{
        this.phones = data.branches.map(branch=>branch.phone.replace(/(?=(?:.{3})*$)/g, ' '));

      },error => {

      }
    )
  }

}
