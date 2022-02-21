export interface FlipCardSectionInfo {
  background: string;
  title: string;
  flipCards: FlipCard[];
}

export interface FlipCard {
  faClass: string;
  cardFrontDescription: string;
  cardBackDescription: string;
}
