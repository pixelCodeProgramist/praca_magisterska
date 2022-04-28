import {
  ActivatedRoute,
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from "@angular/router";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ForgetPasswordResponseGuard implements CanActivate {
  constructor(private router: Router) {
  }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let param = route.queryParams['token'];
    if(param == undefined) this.router.navigate(['/404']);
    if(route.queryParams['token'].trim().length>0) return true;
    this.router.navigate(['/404']);
    return false;
  }


}
