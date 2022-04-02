import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainSiteComponent} from "./web-content/for-everyone/main-site-sections/main-site/main-site.component";
import {AboutUsComponent} from "./web-content/for-everyone/main-navbar-links/about-us/about-us.component";
import {RulesComponent} from "./web-content/for-everyone/main-navbar-links/rules/rules.component";
import {OfferComponent} from "./web-content/for-everyone/main-navbar-links/offer-for-everyone-package/offer/offer.component";
import {ContactComponent} from "./web-content/for-everyone/main-navbar-links/contact/contact.component";
import {PriceListComponent} from "./web-content/for-everyone/main-navbar-links/price-list/price-list.component";
import {LoginComponent} from "./web-content/for-everyone/main-navbar-links/login/login.component";
import {RegisterComponent} from "./web-content/for-everyone/main-navbar-links/register/register.component";
import {
  OfferDetailsComponent
} from "./web-content/for-everyone/main-navbar-links/offer-for-everyone-package/offer-details/offer-details.component";

const routes: Routes = [
  {
    component: MainSiteComponent,
    path: 'mainSite'
  },
  {
    path: '',
    redirectTo: 'mainSite',
    pathMatch: 'full'
  },
  {
    component: AboutUsComponent,
    path: 'about'
  },
  {
    component: RulesComponent,
    path: 'rules'
  },
  {
    component: OfferComponent,
    path: 'offer'
  },
  {
    component: ContactComponent,
    path: 'contact'
  },
  {
    component: PriceListComponent,
    path: 'prices'
  },
  {
    component: LoginComponent,
    path: 'login'
  },
  {
    component: RegisterComponent,
    path: 'register'
  },
  {
    component: OfferDetailsComponent,
    path: 'offer/offer-details'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
