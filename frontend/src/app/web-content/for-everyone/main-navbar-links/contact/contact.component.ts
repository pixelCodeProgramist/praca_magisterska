import {Component, OnInit} from '@angular/core';
import {FlipCard} from "../../../../../models/FlipCardSectionInfo";
import {MapForContact} from "../../../../../business/MapForContact";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SafeResourceUrl} from "@angular/platform-browser";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {ImagesForSectionResponse} from "../../../../../models/general-information/response/ImagesForSectionResponse";
import {
  GeneralInformationResponse
} from "../../../../../models/general-information/response/GeneralInformationResponse";


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  flipCards: FlipCard[] = [];
  maps: MapForContact[] = [];
  map1: MapForContact | undefined;
  map2: MapForContact | undefined;
  public contactFormGroup!: FormGroup;
  sectionUrlsMap: Map<string, SafeResourceUrl[]> = new Map<string, SafeResourceUrl[]>();

  placesSection: ImagesForSectionResponse[] = [];

  generalInfo!: GeneralInformationResponse;
  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService) {
  }

  ngOnInit(): void {
    this.generalInformationService.getLinks('contact-section').subscribe(
      links => {
        this.generalInformationService.getPhotos(links.filter(e=>e.fileName.includes('place'))).subscribe(
          data=>{
            this.placesSection = data;
            for(let placeSection of this.placesSection) {
              let objectURL = 'data:image/png;base64,' + placeSection.image;
              let safeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
              placeSection.imageSafeUrl = safeUrl;
            }
            setTimeout( () => {
              this.generalInformationService.getGeneralInfo().subscribe(generalInfo=>{
                this.generalInfo = generalInfo
                this.setMapInfo();
                this.setFlipCards();
              },error => {

              })}, 100 );
          },error => {
          }
        )
        for (let i = 0; i < links.length; i++) {
          let safeUrls: SafeResourceUrl[] = [];

          this.generalInformationService.getPhoto(links[i].url).subscribe(
            data => {
              safeUrls.push(this.imageFromByteSanitizer.convertToSaveUrl(data));
              this.sectionUrlsMap.set(links[i].fileName, safeUrls);
            }, error => {
            }
          )
        }
      }, error => {
      }
    );

    this.setReactiveFormGroup();
  }

  private setFlipCards() {
    let address = ''
    for(let branch of this.generalInfo.branches) {
      address += '<p>'+branch.street+', '+branch.zipCode+' '+branch.city+'</p>';
    }

    let faClassesTypes = ['address','email']
    let cardFrontDescriptions = ['Adres','Email']
    let cardBackDescriptions = [address, this.generalInfo.email];

    this.generalInfo.branches.forEach((branch, index) => {
      cardBackDescriptions.push(branch.phone.replace(/(?=(?:.{3})*$)/g, ' '))
      faClassesTypes.push('phone')
      cardFrontDescriptions.push('Telefon '+(index+1));
    });

    for(let i=0; i<faClassesTypes.length;i++) {
      let faClass = this.getFaClass(faClassesTypes[i]);
      this.flipCards.push(new class implements FlipCard {
        cardBackDescription: string = cardBackDescriptions[i];
        cardFrontDescription: string = cardFrontDescriptions[i];
        faClass: string = faClass;
      })
    }
  }

  private getFaClass(type: string) {
    if(type == 'address') return 'fa fa-map-marker';
    if(type == 'phone') return 'fa fa-map-marker';
    if(type == 'email') return 'fa fa-envelope';
    return '';
  }

  private setReactiveFormGroup() {
    this.contactFormGroup = new FormGroup({
      firstLastName: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      message: new FormControl('', [Validators.required])
    });
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.contactFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.contactFormGroup!.controls[controlName].errors != null && this.contactFormGroup!.controls[controlName].touched;
  }

  displayPlaceName(fileName: string) {
    return fileName.replace('place','Punkt ');
  }

  private setMapInfo() {

    for(let branch of this.generalInfo.branches) {
      let map = new MapForContact([branch.latitude, branch.longitude],'map'+branch.branchId,
        'Punkt '+branch.branchId+' - ' + branch.street + ', ' + branch.zipCode + ' ' + branch.city);
      map.initMap();
      this.maps.push(map);

    }

  }
}
