import {Component, Input, OnInit} from '@angular/core';
import {City} from "../../objects/city";
import {User} from "../../objects/user";
import {Role} from "../../objects/role";
import {ActivatedRoute, NavigationExtras, Router} from "@angular/router";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  public error: boolean = false;
  @Input() city: City | null = null;
  @Input() user: User | null = null;

  constructor(private router: Router) {
  }

  showError() {
    this.error = true;
  }

  ngOnInit(): void {
  }

  public editPicture() {
    const navigationExtras: NavigationExtras = {
      state: {
        customData: this.city
      }
    };
    this.router.navigate(['/edit'],navigationExtras);
  }

  public isEditor(): boolean {
    let roles: Role[] = this.user?.roles || [];
    return roles.filter(role => {
      return role.name === 'EDITOR'
    }).length > 0;
  }
}
