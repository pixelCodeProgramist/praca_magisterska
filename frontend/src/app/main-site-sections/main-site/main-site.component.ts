import {AfterViewInit, Component, HostListener, OnInit} from '@angular/core';

@Component({
  selector: 'app-main-site',
  templateUrl: './main-site.component.html',
  styleUrls: ['./main-site.component.css']
})
export class MainSiteComponent implements OnInit {

  ngOnInit() {
  }

  constructor() {
  }

  @HostListener('window:scroll', ['$event'])
  @HostListener('window:beforeunload', ['$event'])
  onScroll(event: any) {
    let elements: any[] = this.getElementsOfClass('.first-h-unactive');
    this.replaceClasses(elements,'.first-h-unactive', 'first-h');
  }

  replaceClasses(elements: any[],removeClass: string, addedClass: string) {
    for (let element of elements) {
     this.replaceClass(element,removeClass,addedClass);
    }
  }

  replaceClass(element: any,removeClass: string, addedClass: string) {
      if(this.checkVisible(element)) {
        addedClass = addedClass.replace('.', '');
        element.classList.remove(removeClass);
        element.classList.add(addedClass);
      }

  }

  getElementsOfClass(searchedClass: string) {
    return Array.from(document.querySelectorAll(searchedClass));
  }

  checkVisible(elm: any) {
    var rect = elm.getBoundingClientRect();
    var viewHeight = Math.max(document.documentElement.clientHeight, window.innerHeight);
    return !(rect.bottom < 0 || rect.top - viewHeight >= 0);
  }

}
