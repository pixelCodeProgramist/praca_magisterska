import {AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {GeneralInformationService} from "../../shared/general-information.service";
import {UserService} from "../../shared/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, AfterViewInit {
  isCollapsed = true;
  phones: string[] = [];
  name: string = '';
  role: string = '';
  @ViewChild('userButton') userButton!: ElementRef<HTMLElement>;

  constructor(private generalInformationService: GeneralInformationService, public userService: UserService) { }

  ngOnInit(): void {

    if (this.userService.isLoggedIn() && localStorage.getItem('name') !== null) {
      this.name = localStorage.getItem('name')!.toString();
      this.role = localStorage.getItem('role')!.toString();
    }

    this.generalInformationService.getGeneralInfo().subscribe(
      data=>{
        this.phones = data.branches.map(branch=>branch.phone.replace(/(?=(?:.{3})*$)/g, ' '));

      },error => {

      }
    )
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

  ngAfterViewInit(): void {
    if(this.userButton!=undefined) {
      this.userButton.nativeElement.className += this.getClassFromRole(localStorage.getItem('role'))
    }
  }

  getClassFromRole(role: string | null) {
    if (role === 'ADMIN') return ' btn-outline-danger';
    if (role === 'CLIENT') return ' btn-outline-primary';
    if (role === 'EMPLOYEE') return ' btn-outline-warning';
    return '';
  }


}
