import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from "@angular/router";
import {RoleGuardService} from "./role-guard-service";

@Injectable()
export class UserGuardService extends RoleGuardService implements CanActivate {
  constructor() {
    super();
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.properties != null) {
      return this.properties.roles.filter(role => {
        return role.name === 'USER'
      }).length > 0;
    }
    return false;
  }
}
