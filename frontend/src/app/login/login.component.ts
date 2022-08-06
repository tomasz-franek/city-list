import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpErrorResponse} from "@angular/common/http";
import {AuthenticationService} from "../../services/authentication-service";

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loading = false;
  public username: string = '';
  public password: string = '';

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this.authenticationService.logout();
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.username, this.password)
      .subscribe({
        next: () => {
          this.router.navigateByUrl('dashboard');
        },
        error: (error: HttpErrorResponse) => {
          alert(error.statusText);
          this.loading = false;
        }
      });
  }
}
