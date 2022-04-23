import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-popup-information-view',
  templateUrl: './popup-information-view.component.html',
  styleUrls: ['./popup-information-view.component.css']
})
export class PopupInformationViewComponent implements OnInit {

  @Input() message!: string;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
