import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainSiteComponent } from './main-site-sections/main-site/main-site.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FlipCardSectionComponent } from './main-site-sections/flip-card-section/flip-card-section.component';
import {LightboxModule} from "ngx-lightbox";
import { PhotoSectionComponent } from './main-site-sections/photo-section/photo-section.component';
import { MainProductsPriceComponentComponent } from './main-site-sections/main-products-price-component/main-products-price-component.component';
import { CarouselComponent } from './main-site-sections/carousel/carousel.component';
import { TimeSectionComponent } from './main-site-sections/time-section/time-section.component';
import { AboutUsComponent } from './main-navbar-links/about-us/about-us.component';
import { RulesComponent } from './main-navbar-links/rules/rules.component';
import { OfferComponent } from './main-navbar-links/offer/offer.component';
import { ContactComponent } from './main-navbar-links/contact/contact.component';
import { PriceListComponent } from './main-navbar-links/price-list/price-list.component';
import { LoginComponent } from './main-navbar-links/login/login.component';
import { RegisterComponent } from './main-navbar-links/register/register.component';
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import {ReactiveFormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    MainSiteComponent,
    HeaderComponent,
    FooterComponent,
    FlipCardSectionComponent,
    PhotoSectionComponent,
    MainProductsPriceComponentComponent,
    CarouselComponent,
    TimeSectionComponent,
    AboutUsComponent,
    RulesComponent,
    OfferComponent,
    ContactComponent,
    PriceListComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LightboxModule,
    LeafletModule,
    NgbModule,

    BrowserAnimationsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
