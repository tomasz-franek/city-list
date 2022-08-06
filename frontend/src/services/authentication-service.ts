import {HttpClient, HttpHeaders} from "@angular/common/http";
import {DataService} from "./data-service";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient) {
  }

  login(username: string, password: string) {

    let auth = btoa(username + ':' + password);
    return this.http.post<any>('user/login', '', {
      headers: new HttpHeaders().set('Authorization', 'Basic ' + auth),
      observe: 'response', withCredentials: true
    }).pipe(
      map(response => {
        if (response) {
          let token = response.headers.get('TOKEN') || '';
          localStorage.setItem('USER',JSON.stringify(response.body))
          localStorage.setItem('TOKEN', token );
        }
        return response;
      }));
  }

  logout() {
    localStorage.removeItem('TOKEN');
    localStorage.removeItem('USER');
  }
}
