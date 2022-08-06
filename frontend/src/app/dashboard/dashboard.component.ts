import { Component, OnInit } from '@angular/core';
import {User} from "../../objects/user";
import {DataService} from "../../services/data-service";
import {City} from "../../objects/city";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication-service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public user: User = new User();
  public page:number = 0;
  private size:number = 8;
  public totalPages:number = 0;
  public cityName:string = '';
  public cities:City[] = [];
  constructor(public dataService: DataService,
              private authenticationService: AuthenticationService,
              private router: Router) {
    this.user = JSON.parse(localStorage.getItem('USER') || '{}');

  }

  ngOnInit(): void {
    if(!this.user || this.user.idUser == 0){
      this.router.navigateByUrl('login');
    }
    this.setPage(0);
  }

  public setPage(page:number){
    this.page = page;

    this.getCitiesData();
  }

  private getCitiesData() {
    this.dataService.getCities(this.page, this.size, this.cityName).subscribe(
      {
        next: (data: any) => {
          this.cities = data.content;
          this.totalPages = data.totalPages;
        },
        error: (err) => {
          alert(err);
        }
      }
    )
  }

  public logoutUser(){
    this.authenticationService.logout();
    this.router.navigateByUrl('login');
  }

  public searchByName(event:string){
    this.cityName = event;
    this.page = 0;
    this.getCitiesData();
  }
}
