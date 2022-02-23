import { Component, OnInit } from '@angular/core';
import {TimePriceModel} from "../../../models/TimePriceModel";
import {MainProduct} from "../../../models/MainProduct";


@Component({
  selector: 'app-main-products-price-component',
  templateUrl: './main-products-price-component.component.html',
  styleUrls: ['./main-products-price-component.component.css']
})
export class MainProductsPriceComponentComponent implements OnInit {
  isBikeActive: boolean = true;
  timePrices: TimePriceModel[] = [
    new class implements TimePriceModel {
      time: string = 'do 3h';
      price: number = 120;
    },
    new class implements TimePriceModel {
      time: string = 'do 5h';
      price: number = 180;
    },
    new class implements TimePriceModel {
      time: string = 'do 8h';
      price: number = 220;
    },
    new class implements TimePriceModel {
      time: string = 'doba';
      price: number = 250;
    },
  ]
  mainProducts: MainProduct[] = [
    new class implements MainProduct {
      type: string = "Rower STANDARD";
      everyBeginHour: string = '10';
      day: number = 40;
      dayAndNight: number = 50;
    },
    new class implements MainProduct {
      type: string = "Rower PLUS";
      everyBeginHour: string = '12';
      day: number = 50;
      dayAndNight: number = 60;
    },
    new class implements MainProduct {
      type: string = "Rower PREMIUM";
      everyBeginHour: string = '20';
      day: number = 80;
      dayAndNight: number = 90;
    },
    new class implements MainProduct {
      type: string = "Fotelik";
      everyBeginHour: string = 'Jednorazowo';
      day: number = 15;
      dayAndNight: number = 15;
    },
    new class implements MainProduct {
      type: string = "Riksza";
      everyBeginHour: string = '10';
      day: number = 40;
      dayAndNight: number = 50;
    },
    new class implements MainProduct {
      type: string = "Riksza do pchania";
      everyBeginHour: string = '10';
      day: number = 50;
      dayAndNight: number = 60;
    },
    new class implements MainProduct {
      type: string = "Riksza Premium";
      everyBeginHour: string = '15';
      day: number = 60;
      dayAndNight: number = 70;
    },
    new class implements MainProduct {
      type: string = "Riksza Premium do pchania";
      everyBeginHour: string = '15';
      day: number = 70;
      dayAndNight: number = 80;
    },
    new class implements MainProduct {
      type: string = "Nosidełko turystyczne";
      everyBeginHour: string = 'Jednorazowo';
      day: number = 30;
      dayAndNight: number = 35;
    }
  ];

  constructor() { }

  ngOnInit(): void {
    this.isBikeActive = true;
  }

  changeStateButton() {
    this.isBikeActive = !this.isBikeActive;
  }

  isNumber(numStr: string) {
    return !isNaN(Number(numStr));
  }

}
