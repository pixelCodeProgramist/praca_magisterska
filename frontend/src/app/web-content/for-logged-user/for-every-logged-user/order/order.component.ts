import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../../shared/user.service";
import {Lightbox} from "ngx-lightbox";
import {OrderService} from "../../../../../shared/order.service";
import {HistoryTransactionResponse} from "../../../../../models/order/response/HistoryTransactionResponse";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {PhotoMainSiteSecondSection} from "../../../../../models/PhotoMainSiteSecondSection";
import {SafeResourceUrl} from "@angular/platform-browser";
import {DetailUserMoreResponse} from "../../../../../models/detail-user/DetailUserMoreResponse";
import {ErrorHandler} from "../../../../../shared/ErrorHandler";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  keyword = 'firstName';
  detailClientResponse: DetailUserMoreResponse = new DetailUserMoreResponse()
  detailClientResponses: DetailUserMoreResponse[] = []

  myHistoryAlbums: PhotoMainSiteSecondSection[] = [];
  otherHistoryAlbums: PhotoMainSiteSecondSection[] = [];

  currentMyHistoryPage: number = 1;
  currentOtherHistoryPage: number = 1;
  isErrorActive: boolean = false;
  errorMsg: string = '';


  constructor(public userService: UserService, private orderService: OrderService, private lightbox: Lightbox,  private imageFromByteSanitizer: ImageFromByteSanitizerService) { }

  myHistory: HistoryTransactionResponse = new HistoryTransactionResponse();
  otherHistory: HistoryTransactionResponse = new HistoryTransactionResponse();
  myHistoryLoading: boolean = false;
  otherHistoryLoading: boolean = false;


  ngOnInit(): void {
    this.myHistoryLoading = true;
    this.orderService.getOrderHistory(this.currentMyHistoryPage-1).subscribe(
      data=>{
        this.myHistoryLoading = false;
        this.myHistory = data;
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

          this.myHistoryAlbums.push(album);
        })
      },error => {
        this.myHistoryLoading = false;
        this.isErrorActive = true
        let errorHandler: ErrorHandler = new ErrorHandler();
        this.errorMsg = errorHandler.handle(error,this.errorMsg)
      }
    )

    this.userService.getAllClients().subscribe(
      data => {
        this.detailClientResponses = data;
        this.detailClientResponse = this.detailClientResponses[0];
      }, error => {
        this.isErrorActive = true
        let errorHandler: ErrorHandler = new ErrorHandler();
        this.errorMsg = errorHandler.handle(error,this.errorMsg)
      });
  }

  open(i: number, albums: PhotoMainSiteSecondSection[]) {
    // @ts-ignore
    this.lightbox.open(albums, i);
  }

  clickOnMyHistoryPage($event: number) {
    this.currentMyHistoryPage = $event;
    this.ngOnInit();
  }


  search($event: DetailUserMoreResponse) {
    this.detailClientResponse = $event
    this.searchOtherUserOffer();
  }

  clickOnOtherHistoryPage($event: number) {
    this.currentOtherHistoryPage = $event;
    this.searchOtherUserOffer();
  }

  searchOtherUserOffer() {

    this.otherHistoryLoading = true;
    this.orderService.getOrderHistory(this.currentOtherHistoryPage-1, this.detailClientResponse.userId).subscribe(
      data=>{
        this.otherHistoryLoading = false;
        this.otherHistoryAlbums = [];
        this.otherHistory = data;
        this.otherHistory.orderHistoryList.forEach(history=> {

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


          this.otherHistoryAlbums.push(album);
        });
      },error => {
        this.otherHistoryLoading = false;

      });
  }
}
