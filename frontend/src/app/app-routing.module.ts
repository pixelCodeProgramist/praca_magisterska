import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainSiteComponent} from "./web-content/for-everyone/main-site-sections/main-site/main-site.component";
import {AboutUsComponent} from "./web-content/for-everyone/main-navbar-links/about-us/about-us.component";
import {RulesComponent} from "./web-content/for-everyone/main-navbar-links/rules/rules.component";
import {
  OfferComponent
} from "./web-content/for-everyone/main-navbar-links/offer-for-everyone-package/offer/offer.component";
import {ContactComponent} from "./web-content/for-everyone/main-navbar-links/contact/contact.component";
import {PriceListComponent} from "./web-content/for-everyone/main-navbar-links/price-list/price-list.component";
import {LoginComponent} from "./web-content/for-everyone/main-navbar-links/login-package/login/login.component";
import {RegisterComponent} from "./web-content/for-everyone/main-navbar-links/register/register.component";
import {
  OfferDetailsComponent
} from "./web-content/for-everyone/main-navbar-links/offer-for-everyone-package/offer-details/offer-details.component";
import {
  ForgetPasswordComponent
} from "./web-content/for-everyone/main-navbar-links/login-package/forget-password/forget-password.component";
import {
  ForgetPasswordResponseComponent
} from "./web-content/for-everyone/main-navbar-links/login-package/forget-password-response/forget-password-response.component";
import {ForgetPasswordResponseGuard} from "../shared/guard/forget-password-response-guard.service";
import {NotFoundSiteComponent} from "./not-found-site/not-found-site.component";
import {UserPanelComponent} from "./web-content/for-logged-user/user-panel/user-panel.component";

import {AuthenticationGuard} from "../shared/guard/AuthenticationGuard.guard";
import {UnauthenticationGuard} from "../shared/guard/UnauthenticationGuard.guard";
import {SettingAccountComponent} from "./web-content/for-logged-user/for-every-logged-user/setting-account/setting-account.component";
import {RepairBikeComponent} from "./web-content/for-logged-user/for-every-logged-user/repair-bike/repair-bike.component";
import {OrderComponent} from "./web-content/for-logged-user/for-every-logged-user/order/order.component";
import {
  OfferManagementComponent
} from "./web-content/for-logged-user/for-employee/offer-management/offer-management.component";
import {RoleGuard} from "../shared/guard/role-guard.service";
import {Role} from "../shared/enum/Role";
import {
  EmployeeManagementComponent
} from "./web-content/for-logged-user/for-admin/employee-management/employee-management.component";
import {
  ClientManagementComponent
} from "./web-content/for-logged-user/for-employee/client-management-package/client-management/client-management.component";
import {
  StatisticManagementComponent
} from "./web-content/for-logged-user/for-employee/statistic-management/statistic-management.component";

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
    path: 'login',
    canActivate: [UnauthenticationGuard],
  },
  {
    component: RegisterComponent,
    path: 'register',
    canActivate: [UnauthenticationGuard],
  },
  {
    component: OfferDetailsComponent,
    path: 'offer/offer-details'
  },
  {
    component: ForgetPasswordComponent,
    path: 'forget_password',
    canActivate: [UnauthenticationGuard],
  },
  {
    component: ForgetPasswordResponseComponent,
    path: 'forget_password_mail_response',
    canActivate: [ForgetPasswordResponseGuard]
  },
  {
    component: UserPanelComponent,
    path: 'user-panel',
    canActivate: [AuthenticationGuard],
  },
  {
    component: SettingAccountComponent,
    path: 'user-panel/setting',
    canActivate: [AuthenticationGuard],
  },
  {
    component: RepairBikeComponent,
    path: 'user-panel/repair-bike',
    canActivate: [AuthenticationGuard],
  },
  {
    component: OrderComponent,
    path: 'user-panel/order-history',
    canActivate: [AuthenticationGuard],
  },
  {
    component: OfferManagementComponent,
    path: 'user-panel/offer-management',
    canActivate: [AuthenticationGuard, RoleGuard],
    data: {roles: [Role.ADMIN, Role.EMPLOYEE]}
  },
  {
    component: EmployeeManagementComponent,
    path: 'user-panel/employee-management',
    canActivate: [AuthenticationGuard, RoleGuard],
    data: {roles: [Role.ADMIN]}
  },
  {
    component: ClientManagementComponent,
    path: 'user-panel/client-management',
    canActivate: [AuthenticationGuard, RoleGuard],
    data: {roles: [Role.ADMIN, Role.EMPLOYEE]}
  },
  {
    component: StatisticManagementComponent,
    path: 'user-panel/statistic-management',
    canActivate: [AuthenticationGuard, RoleGuard],
    data: {roles: [Role.ADMIN, Role.EMPLOYEE]}
  },
  {
    path: '404', component: NotFoundSiteComponent
  },
  {
    path: '**', redirectTo: '/404'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
