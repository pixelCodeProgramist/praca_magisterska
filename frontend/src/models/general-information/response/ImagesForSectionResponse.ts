import {Byte} from "@angular/compiler/src/util";
import {SafeResourceUrl} from "@angular/platform-browser";

export interface ImagesForSectionResponse {
  imageId: number;
  url: string;
  fileName: string;
  image: Byte[];
  imageSafeUrl: SafeResourceUrl;
}
