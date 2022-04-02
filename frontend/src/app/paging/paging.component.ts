import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Subject} from "rxjs";

@Component({
  selector: 'app-paging',
  templateUrl: './paging.component.html',
  styleUrls: ['./paging.component.css']
})
export class PagingComponent implements OnInit, OnChanges {
  buttonsValueList: number[] = [1, 2, 3, 4, 5];
  @Input() MAX_PAGINATION: number = 0;
  private status$: Subject<number>;
  @Output() currentPageEmitter = new EventEmitter<number>();
  buttonNumber = 0;
  private currentPage: number = 1;


  constructor() {
    this.status$ = new Subject<number>();
  }

  ngOnInit(): void {
    if (this.MAX_PAGINATION > 5) this.buttonNumber = this.MAX_PAGINATION;
    else this.buttonNumber = 5;

  }


  select(id: string, buttonValue: number) {
    if (this.currentPage !== buttonValue) {
      let selectors = document.getElementsByClassName('active-non-active');
      for (let i = 0; i < selectors.length; i++) {
        selectors[i].className = selectors[i].className.replace(' active-button', '');
      }
      let selector: HTMLElement | null = document.getElementById(id);
      if (selector !== null)
        selector.className += ' active-button';
      this.currentPage = buttonValue;
      this.currentPageEmitter.emit(buttonValue);

    }

  }

  increase() {
    if (this.buttonsValueList[this.buttonsValueList.length - 1] < this.MAX_PAGINATION) {
      for (var i = 0; i < this.buttonsValueList.length; i++){
        this.buttonsValueList[i] += 1;
      }
    }

  }

  decrease() {
    if (this.buttonsValueList[0] > 1) {
      for (var i = 0; i < this.buttonsValueList.length; i++){
        this.buttonsValueList[i] -= 1;
      }
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.buttonsValueList = [1, 2, 3, 4, 5];
    this.status$.next(this.MAX_PAGINATION);
    this.buttonNumber = this.MAX_PAGINATION;
    if (this.buttonsValueList.length > this.buttonNumber && this.buttonNumber > 0)
      this.buttonsValueList = this.buttonsValueList.slice(0, this.buttonNumber);
    let selectors = document.getElementsByClassName('active-non-active');
    let noActiveNumber = 0;
    for (let i = 0; i < selectors.length; i++) {
      if (!selectors[i].className.includes('active-button')) noActiveNumber++;
    }
  }
}
