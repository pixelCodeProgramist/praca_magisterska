import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainSiteComponent} from "./main-site-sections/main-site/main-site.component";

const routes: Routes = [
  {
    component: MainSiteComponent,
    path: 'mainSite'
  },
  {
    path: '',
    redirectTo: 'mainSite',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
