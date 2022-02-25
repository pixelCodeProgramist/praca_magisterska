import {Component, HostListener, OnInit} from '@angular/core';

@Component({
  selector: 'app-rules',
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.css']
})
export class RulesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event: any) {
    let scrollableList = Array.from(document.getElementsByClassName('scrollable') as HTMLCollectionOf<HTMLElement>);
    let value: number = -window.scrollY;
    if(value>-250 && scrollableList[0].clientWidth>1210) {
      scrollableList.forEach((scrollable, index) => {
        if(index > 0) value*=0.01;
        scrollable!.style.transform = 'translateY(calc(-25% - '+value+'px)';
      })
    }
  }

}
