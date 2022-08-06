import {User} from "../objects/user";

export class RoleGuardService {
  public readonly properties: User | null = null;

  constructor() {
    let storageProperties = localStorage.getItem('USER');
    if (storageProperties) {
      this.properties = JSON.parse(storageProperties);
    }
  }
}
