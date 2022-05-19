import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AddOfferRequest, Frame} from "../../../../../../models/offers/request/AddOfferRequest";
import {OfferService} from "../../../../../../shared/offer.service";
import {Byte} from "@angular/compiler/src/util";
import {
  PopupInformationViewComponent
} from "../../../../for-everyone/main-navbar-links/popup-information-view/popup-information-view.component";
import {ErrorHandler} from "../../../../../../shared/ErrorHandler";

@Component({
  selector: 'app-add-offer-content-management',
  templateUrl: './add-offer-content-management.component.html',
  styleUrls: ['./add-offer-content-management.component.css']
})
export class AddOfferContentManagementComponent implements OnInit {

  public addOfferFormGroup!: FormGroup;
  frames: string[] = ['S', 'M', 'L', 'XL'];
  framesStates: boolean[] = [false, false, false, false];
  addOfferRequest: AddOfferRequest = new AddOfferRequest();

  isErrorActive: boolean = false;
  errorMsg: string = '';
  loading: boolean = false;

  constructor(private offerService: OfferService, private ngbModal: NgbModal) {
  }


  ngOnInit(): void {
    this.addOfferRequest.selectedProductTypeOption = 'Rower klasyczny';
    this.addOfferRequest.selectedBikeOrAccessoryTypeOption = 'Standard';

    this.addOfferFormGroup = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
    });
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.addOfferFormGroup.controls[controlName].hasError(errorName);
  }

  public hasError = (controlName: string) => {
    return this.addOfferFormGroup!.controls[controlName]?.errors != null && this.addOfferFormGroup!.controls[controlName].touched;
  }


  submit() {
    if (this.addOfferFormGroup.valid) {
      this.loading = true;
      if (this.addOfferRequest.image.length == 0) {
        this.isErrorActive = true;
        this.errorMsg = 'Brak zdjęcia';
      }
      if (this.addOfferRequest.frames.length == 0) {
        this.isErrorActive = true;
        this.errorMsg = 'Nie dodano rozmiaru ram roweru';
      } else {
        let zeroInstance = 0;
        for (let frame of this.addOfferRequest.frames) {
          if (frame.quantity == 0) {
            this.isErrorActive = true;
            this.errorMsg = 'Nie można dodać ramy z ilością rowerów 0';
            zeroInstance++;
          }
        }
        if (zeroInstance == 0) {
          this.offerService.addBike(this.addOfferRequest).subscribe(
            data => {
              this.loading = false;
              const modalRef = this.ngbModal.open(PopupInformationViewComponent);
              modalRef.componentInstance.message = data.message;
              this.addOfferFormGroup.reset();
            }, error => {
              this.loading = false;
              this.isErrorActive = true;
              let errorHandler: ErrorHandler = new ErrorHandler();
              this.errorMsg = errorHandler.handle(error,this.errorMsg)
            }
          )
        }
      }
    } else {
      this.isErrorActive = true;
      this.errorMsg = 'Formularz zawiera błędy';
    }

  }

  changeStatusOfFrameCheckbox(i: number) {
    this.framesStates[i] = !this.framesStates[i];
    if (this.framesStates[i]) {
      this.addOfferRequest.frames.push(new Frame(this.frames[i], 0));
    } else {
      this.addOfferRequest.frames = this.addOfferRequest.frames.filter(frame => frame.frameSize != this.frames[i]);
    }

  }


  onSelectFile($event: any) {
    let reader = new FileReader();
    let fileByteArray: Byte[] = [];

    const element = $event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if (fileList) {
      let file = fileList[0];
      reader.readAsArrayBuffer(file);
      reader.onloadend = function (evt) {
        if (evt.target?.readyState == FileReader.DONE) {
          // @ts-ignore
          let arrayBuffer = evt.target.result, array = new Uint8Array(arrayBuffer);
          for (let i = 0; i < array.length; i++) {
            fileByteArray.push(array[i]);
          }
        }
      }
      this.addOfferRequest.image = fileByteArray;
    }


  }

}
