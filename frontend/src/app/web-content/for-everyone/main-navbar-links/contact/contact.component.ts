import {Component, OnInit} from '@angular/core';
import {FlipCard} from "../../../../../models/FlipCardSectionInfo";
import {MapForContact} from "../../../../../business/MapForContact";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SafeResourceUrl} from "@angular/platform-browser";
import {GeneralInformationService} from "../../../../../shared/general-information.service";
import {ImageFromByteSanitizerService} from "../../../../../shared/ImageFromByteSanitizer.service";
import {ImageForSectionResponse} from "../../../../../models/general-information/response/ImageForSectionResponse";
import {
  GeneralInformationResponse
} from "../../../../../models/general-information/response/GeneralInformationResponse";
import {ContactRequest} from "../../../../../models/contact/ContactRequest";
import {ContactService} from "../../../../../shared/contact.service";


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  flipCards: FlipCard[] = [];
  maps: MapForContact[] = [];
  contactFormGroup!: FormGroup;
  imagesForSectionResponse: ImageForSectionResponse[] = [];

  generalInfo!: GeneralInformationResponse;

  constructor(private generalInformationService: GeneralInformationService, private imageFromByteSanitizer: ImageFromByteSanitizerService, private contactService: ContactService) {
  }

  ngOnInit(): void {
    this.generalInformationService.getPhotosForSection('contact-section').subscribe(
      data => {
        this.imagesForSectionResponse = data;

        for (let placeSection of this.imagesForSectionResponse) {
          let objectURL = 'data:image/png;base64,' + placeSection.image;
          let safeUrl = this.imageFromByteSanitizer.convertToSaveUrlFromString(objectURL);
          placeSection.imageSafeUrl = safeUrl;
        }
        this.generalInformationService.getGeneralInfo().subscribe(generalInfo => {
          this.generalInfo = generalInfo
          this.setMapInfo();
          this.setFlipCards();
        }, error => {

        });


      }, error => {
      }
    );

    this.setReactiveFormGroup();
  }

  private setFlipCards() {
    let address = ''
    for (let branch of this.generalInfo.branches) {
      address += '<p>' + branch.street + ', ' + branch.zipCode + ' ' + branch.city + '</p>';
    }

    let faClassesTypes = ['address', 'email']
    let cardFrontDescriptions = ['Adres', 'Email']
    let cardBackDescriptions = [address, this.generalInfo.email];

    this.generalInfo.branches.forEach((branch, index) => {
      cardBackDescriptions.push(branch.phone.replace(/(?=(?:.{3})*$)/g, ' '))
      faClassesTypes.push('phone')
      cardFrontDescriptions.push('Telefon ' + (index + 1));
    });

    for (let i = 0; i < faClassesTypes.length; i++) {
      let faClass = this.getFaClass(faClassesTypes[i]);
      this.flipCards.push(new class implements FlipCard {
        cardBackDescription: string = cardBackDescriptions[i];
        cardFrontDescription: string = cardFrontDescriptions[i];
        faClass: string = faClass;
      })
    }
  }

  private getFaClass(type: string) {
    if (type == 'address') return 'fa fa-map-marker';
    if (type == 'phone') return 'fa fa-map-marker';
    if (type == 'email') return 'fa fa-envelope';
    return '';
  }

  private setReactiveFormGroup() {
    this.contactFormGroup = new FormGroup({
      firstLastName: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]),
      subject: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      message: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(1000)])
    });
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.contactFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.contactFormGroup!.controls[controlName].errors != null && this.contactFormGroup!.controls[controlName].touched;
  }

  displayPlaceName(fileName: string) {
    return fileName.replace('place', 'Punkt ');
  }

  private setMapInfo() {

    for (let branch of this.generalInfo.branches) {
      let map = new MapForContact([branch.latitude, branch.longitude], 'map' + branch.branchId,
        'Punkt ' + branch.branchId + ' - ' + branch.street + ', ' + branch.zipCode + ' ' + branch.city);
      map.initMap();
      this.maps.push(map);

    }

  }

  filterImagesForSectionResponse(type: string) {
    return this.imagesForSectionResponse.filter(place => place.fileName.includes(type))
  }

  sendMail() {
    if (this.contactFormGroup.valid) {
      let contact: ContactRequest = new ContactRequest(
        this.contactFormGroup.controls['subject']?.value, this.contactFormGroup.controls['message']?.value,
        this.contactFormGroup.controls['email']?.value, this.contactFormGroup.controls['firstLastName']?.value
      );
      this.contactService.sendMailForContact(contact).subscribe(
        data=>{
          this.contactFormGroup.reset();
        },error => {

        }
      )

    }
  }
}
