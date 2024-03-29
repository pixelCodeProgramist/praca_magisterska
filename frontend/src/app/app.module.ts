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
import { LoginComponent } from './web-content/for-everyone/main-navbar-links/login-package/login/login.component';
import { RegisterComponent } from './web-content/for-everyone/main-navbar-links/register/register.component';
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainProductsPriceContentComponent } from './web-content/for-everyone/main-products-price-content/main-products-price-content.component';
import { PagingComponent } from './paging/paging.component';
import {NgxStarsModule} from "ngx-stars";
import { OfferDetailsComponent } from './web-content/for-everyone/main-navbar-links/offer-for-everyone-package/offer-details/offer-details.component';
import {DatepickerModule} from "ng2-datepicker";
import {NgxDatePickerModule} from "@ngx-tiny/date-picker";
import {HttpClientModule} from "@angular/common/http";
import {AutocompleteLibModule} from "angular-ng-autocomplete";
import { PopupInformationViewComponent } from './web-content/for-everyone/main-navbar-links/popup-information-view/popup-information-view.component';
import { ForgetPasswordComponent } from './web-content/for-everyone/main-navbar-links/login-package/forget-password/forget-password.component';
import { ForgetPasswordResponseComponent } from './web-content/for-everyone/main-navbar-links/login-package/forget-password-response/forget-password-response.component';
import { NotFoundSiteComponent } from './not-found-site/not-found-site.component';
import { UserPanelComponent } from './web-content/for-logged-user/user-panel/user-panel.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatListModule} from "@angular/material/list";
import { SettingAccountComponent } from './web-content/for-logged-user/for-every-logged-user/setting-account/setting-account.component';
import { RepairBikeComponent } from './web-content/for-logged-user/for-every-logged-user/repair-bike/repair-bike.component';
import { OrderComponent } from './web-content/for-logged-user/for-every-logged-user/order/order.component';
import { OfferManagementComponent } from './web-content/for-logged-user/for-employee/offer-management-package/offer-management/offer-management.component';
import { EmployeeManagementComponent } from './web-content/for-logged-user/for-admin/employee-management/employee-management.component';
import { ClientManagementComponent } from './web-content/for-logged-user/for-employee/client-management-package/client-management/client-management.component';
import { StatisticManagementComponent } from './web-content/for-logged-user/for-employee/statistic-management/statistic-management.component';
import { AddEmployeeContentManagementComponent } from './web-content/for-logged-user/for-admin/add-employee-content-management/add-employee-content-management.component';
import { RemoveEmployeeContentManagementComponent } from './web-content/for-logged-user/for-admin/remove-employee-content-management/remove-employee-content-management.component';
import { UpdateEmployeeContentManagementComponent } from './web-content/for-logged-user/for-admin/update-employee-content-management/update-employee-content-management.component';
import { UpdateClientContentManagementComponent } from './web-content/for-logged-user/for-employee/client-management-package/update-client-content-management/update-client-content-management.component';
import { RemoveClientContentManagementComponent } from './web-content/for-logged-user/for-employee/client-management-package/remove-client-content-management/remove-client-content-management.component';
import { AddOfferContentManagementComponent } from './web-content/for-logged-user/for-employee/offer-management-package/add-offer-content-management/add-offer-content-management.component';
import { RemoveOfferContentManagementComponent } from './web-content/for-logged-user/for-employee/offer-management-package/remove-offer-content-management/remove-offer-content-management.component';
import { UpdateOfferContentManagementComponent } from './web-content/for-logged-user/for-employee/offer-management-package/update-offer-content-management/update-offer-content-management.component';
import {NgxLoadingModule} from "ngx-loading";



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
    PopupInformationViewComponent,
    ForgetPasswordComponent,
    ForgetPasswordResponseComponent,
    NotFoundSiteComponent,
    UserPanelComponent,
    SettingAccountComponent,
    RepairBikeComponent,
    OrderComponent,
    OfferManagementComponent,
    EmployeeManagementComponent,
    ClientManagementComponent,
    StatisticManagementComponent,
    AddEmployeeContentManagementComponent,
    RemoveEmployeeContentManagementComponent,
    UpdateEmployeeContentManagementComponent,
    UpdateClientContentManagementComponent,
    RemoveClientContentManagementComponent,
    AddOfferContentManagementComponent,
    RemoveOfferContentManagementComponent,
    UpdateOfferContentManagementComponent
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
    AutocompleteLibModule,
    FormsModule,
    MatSidenavModule,
    MatIconModule,
    MatToolbarModule,
    MatListModule,
    NgxLoadingModule.forRoot({})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
