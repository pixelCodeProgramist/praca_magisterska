import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainSiteComponent } from './web-content/for-everyone/main-site-sections/main-site/main-site.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FlipCardSectionComponent } from './web-content/for-everyone/main-site-sections/flip-card-section/flip-card-section.component';
import {LightboxModule} from "ngx-lightbox";
import { PhotoSectionComponent } from './web-content/for-everyone/main-site-sections/photo-section/photo-section.component';
import { MainProductsPriceOverflowComponent } from './web-content/for-everyone/main-site-sections/main-products-price-overflow/main-products-price-overflow.component';
import { CarouselComponent } from './web-content/for-everyone/main-site-sections/carousel/carousel.component';
import { TimeSectionComponent } from './web-content/for-everyone/main-site-sections/time-section/time-section.component';
import { AboutUsComponent } from './web-content/for-everyone/main-navbar-links/about-us/about-us.component';
import { RulesComponent } from './web-content/for-everyone/main-navbar-links/rules/rules.component';
import { OfferComponent } from './web-content/for-everyone/main-navbar-links/offer-for-everyone-package/offer/offer.component';
import { ContactComponent } from './web-content/for-everyone/main-navbar-links/contact/contact.component';
import { PriceListComponent } from './web-content/for-everyone/main-navbar-links/price-list/price-list.component';
import { LoginComponent } from './web-content/for-everyone/main-navbar-links/login/login.component';
import { RegisterComponent } from './web-content/for-everyone/main-navbar-links/register/register.component';
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import { ReactiveFormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainProductsPriceContentComponent } from './web-content/for-everyone/main-products-price-content/main-products-price-content.component';
import { PagingComponent } from './paging/paging.component';
import {NgxStarsModule} from "ngx-stars";
import { OfferDetailsComponent } from './web-content/for-everyone/main-navbar-links/offer-for-everyone-package/offer-details/offer-details.component';
import {DatepickerModule} from "ng2-datepicker";
import {NgxDatePickerModule} from "@ngx-tiny/date-picker";
import {TimepickerModule} from "ngx-bootstrap/timepicker";
import {HttpClientModule} from "@angular/common/http";
import {AutocompleteLibModule} from "angular-ng-autocomplete";
import { PopupInformationViewComponent } from './web-content/for-everyone/main-navbar-links/popup-information-view/popup-information-view.component';



@NgModule({
  declarations: [
    AppComponent,
    MainSiteComponent,
    HeaderComponent,
    FooterComponent,
    FlipCardSectionComponent,
    PhotoSectionComponent,
    MainProductsPriceOverflowComponent,
    CarouselComponent,
    TimeSectionComponent,
    AboutUsComponent,
    RulesComponent,
    OfferComponent,
    ContactComponent,
    PriceListComponent,
    LoginComponent,
    RegisterComponent,
    MainProductsPriceContentComponent,
    PagingComponent,
    OfferDetailsComponent,
    PopupInformationViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LightboxModule,
    LeafletModule,
    NgbModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    NgxStarsModule,
    DatepickerModule,
    NgxDatePickerModule,
    HttpClientModule,
    AutocompleteLibModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
