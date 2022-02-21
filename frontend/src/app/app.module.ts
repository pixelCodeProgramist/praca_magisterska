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


@NgModule({
  declarations: [
    AppComponent,
    MainSiteComponent,
    HeaderComponent,
    FooterComponent,
    FlipCardSectionComponent,
    PhotoSectionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LightboxModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
