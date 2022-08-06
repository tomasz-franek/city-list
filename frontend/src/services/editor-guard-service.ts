import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from "@angular/router";
import {RoleGuardService} from "./role-guard-service";

@Injectable()
export class EditorGuardService extends RoleGuardService implements CanActivate {
  constructor() {
    super();
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.properties != null) {
      return this.properties.roles.filter(role => {
        return role.name === 'EDITOR'
      }).length > 0;
    }
    return false;
  }
}
