import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../../../shared/user.service";
import {Lightbox} from "ngx-lightbox";
import {OrderService} from "../../../../../shared/order.service";
import {HistoryTransactionResponse} from "../../../../../models/order/response/HistoryTransactionResponse";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {PhotoMainSiteSecondSection} from "../../../../../models/PhotoMainSiteSecondSection";
import {SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  _albums: PhotoMainSiteSecondSection[] = [];

  currentPage: number = 1;

  constructor(public userService: UserService, private orderService: OrderService, private _lightbox: Lightbox,  private imageFromByteSanitizer: ImageFromByteSanitizerService) { }

  myHistory: HistoryTransactionResponse = new HistoryTransactionResponse();

  ngOnInit(): void {
    this.orderService.getOrderHistory(this.currentPage-1).subscribe(
      data=>{
        this.myHistory = data;
        console.log(this.myHistory)
        this.myHistory.orderHistoryList.forEach(history=>{
          history.beginDate = new Date(history.beginDate)
          history.endDate = new Date(history.endDate)
          let objectURL = 'data:image/png;base64,' + history?.qrCodeImage;
          history.imageSafeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
          let image = history.imageSafeUrl;
          let album: PhotoMainSiteSecondSection = new class implements PhotoMainSiteSecondSection {
            caption: string = '';
            src: SafeResourceUrl = image;
            thumb: string = '';
          }

          this._albums.push(album);
        })
      },error => {

      }
    )
  }

  open(i: number) {

    // @ts-ignore
    this._lightbox.open(this._albums, i);
    console.log(this.myHistory.orderHistoryList[i])
  }

  clickOnPage($event: number) {
    this,this.currentPage = $event;
    this.ngOnInit();
  }
}
