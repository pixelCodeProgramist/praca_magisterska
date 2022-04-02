import {SafeResourceUrl} from "@angular/platform-browser";

export interface FlipCardSectionInfo {
  background: SafeResourceUrl;
  title: string;
  flipCards: FlipCard[];
}

export interface FlipCard {
  faClass: string;
  cardFrontDescription: string;
  cardBackDescription: string;
}
