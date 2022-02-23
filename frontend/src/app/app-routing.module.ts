import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainSiteComponent} from "./main-site-sections/main-site/main-site.component";
import {AboutUsComponent} from "./main-navbar-links/about-us/about-us.component";
import {RulesComponent} from "./main-navbar-links/rules/rules.component";
import {OfferComponent} from "./main-navbar-links/offer/offer.component";
import {ContactComponent} from "./main-navbar-links/contact/contact.component";
import {PriceListComponent} from "./main-navbar-links/price-list/price-list.component";
import {LoginComponent} from "./main-navbar-links/login/login.component";
import {RegisterComponent} from "./main-navbar-links/register/register.component";

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
