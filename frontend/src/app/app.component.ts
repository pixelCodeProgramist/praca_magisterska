import {Component, HostListener} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
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
