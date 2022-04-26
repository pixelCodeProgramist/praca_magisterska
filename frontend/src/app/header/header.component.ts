import {Component, OnInit} from '@angular/core';
import {GeneralInformationService} from "../../shared/general-information.service";
import {UserService} from "../../shared/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isCollapsed = true;
  phones: string[] = [];
  name: string = '';
  role: string = '';

  constructor(private generalInformationService: GeneralInformationService, public userService: UserService) { }

  ngOnInit(): void {
    if (this.userService.isLoggedIn() && localStorage.getItem('name') !== null) {
      // @ts-ignore
      this.name = localStorage.getItem('name').toString();
      this.role = localStorage.getItem('role')!.toString();
    }

    this.generalInformationService.getGeneralInfo().subscribe(
      data=>{
        this.phones = data.branches.map(branch=>branch.phone.replace(/(?=(?:.{3})*$)/g, ' '));

      },error => {

      }
    )
  }

  isAdmin(): boolean {
    if (this.role === 'ADMIN')
      return true;
    else
      return false;
  }

  logout() {
    this.userService.logout()
      .subscribe(res => {
          location.reload();
          localStorage.clear();
        },
        error =>{
          // localStorage.clear();
          console.error(error);
      })
  }
}
