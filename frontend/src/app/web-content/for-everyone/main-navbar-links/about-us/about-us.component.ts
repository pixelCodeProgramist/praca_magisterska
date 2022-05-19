import {Component, OnInit} from '@angular/core';
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {ImageForSectionResponse} from "../../../../../models/general-information/response/ImageForSectionResponse";

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css']
})
export class AboutUsComponent implements OnInit {
  imageForSectionResponse: ImageForSectionResponse[] = [];
  loading: boolean = false;
  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.generalInformationService.getPhotosForSection('about-section').subscribe(
      data => {
        this.loading = false;
        this.imageForSectionResponse = data;
        for (let i = 0; i < this.imageForSectionResponse.length; i++) {
          let objectURL = 'data:image/png;base64,' + data[i].image;
          this.imageForSectionResponse[i].imageSafeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
        }
      }, error => {
        this.loading = false;
      }
    );

  }

  filterImages(type: string) {
    return this.imageForSectionResponse.filter(imageForSectionResponse=> imageForSectionResponse.fileName.includes(type))
  }
}
