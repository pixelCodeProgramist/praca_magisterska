import {Injectable, SecurityContext} from "@angular/core";
import {DomSanitizer} from "@angular/platform-browser";

@Injectable({
  providedIn: 'root'
})

export class ImageFromByteSanitizerService {

  constructor(private sanitizer: DomSanitizer) { }

  convertToSaveUrl(data: Blob) {
    return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data));
  }

  convertToSaveUrlFromString(data: string) {
    return this.sanitizer.bypassSecurityTrustUrl(data);
  }
}
