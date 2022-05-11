import {Byte} from "@angular/compiler/src/util";
import {SafeResourceUrl} from "@angular/platform-browser";

export interface HistoryTransaction {
  name: string;
  beginDate: Date;
  endDate: Date;
  price: number;
  currency: string;
  qrCodeImage: Byte[];
  imageSafeUrl: SafeResourceUrl;
}

export class HistoryTransactionResponse {
  maxPages!: number;
  orderHistoryList: HistoryTransaction[] = [];
}
