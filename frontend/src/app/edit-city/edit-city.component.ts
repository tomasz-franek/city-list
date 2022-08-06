import { Component, OnInit } from '@angular/core';
import {City} from "../../objects/city";
import {DataService} from "../../services/data-service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-edit-picture',
  templateUrl: './edit-city.component.html',
  styleUrls: ['./edit-city.component.css']
})
export class EditCityComponent implements OnInit {
  public city:City;
  constructor(public dataService:DataService,
              private route: ActivatedRoute,
              private router:Router) {
    this.city = new City();
  }

  ngOnInit() {
    if(window?.history?.state?.customData){
      this.city = window.history.state.customData;
    }
  }
  public save(){
    if(this.city.cityId != 0) {
      this.dataService.saveCity(this.city).subscribe({
        next: () => {
          alert('City data saved');
        },
        error: () => {
          alert('Error during saving' );
        }
      });
    }
  }

  public backToDashboard(){
    this.router.navigateByUrl('dashboard');
  }
}
