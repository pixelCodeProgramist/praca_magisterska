import {Byte} from "@angular/compiler/src/util";
import {SafeResourceUrl} from "@angular/platform-browser";

export interface ImageForSectionResponse {
  imageId: number;
  url: string;
  fileName: string;
  image: Byte[];
  imageSafeUrl: SafeResourceUrl;
}
