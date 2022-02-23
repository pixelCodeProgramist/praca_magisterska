import {Component, OnInit, ViewChild} from '@angular/core';
import {NgbCarousel} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit {
  @ViewChild('carousel', {static: true})
  carousel!: NgbCarousel;
  images: string[] =['assets/photo1.png','assets/photo2.png','assets/photo3.png','assets/photo4.png']

  constructor() {
  }

  ngOnInit(): void {

  }


  prev() {
    this.carousel.prev();
  }

  next() {
    this.carousel.next();
  }

}
