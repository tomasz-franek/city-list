import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { CardComponent } from './card/card.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EditCityComponent } from './edit-city/edit-city.component';
import {FormsModule} from "@angular/forms";
import {AuthenticationService} from "../services/authentication-service";
import {HttpClientModule} from "@angular/common/http";
import {DataService} from "../services/data-service";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {AuthGuardService} from "../services/auth-guard-service";
import {UserGuardService} from "../services/user-guard-service";
import {EditorGuardService} from "../services/editor-guard-service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CardComponent,
    DashboardComponent,
    EditCityComponent,
    PageNotFoundComponent
  ],
	imports: [
		BrowserModule,
		AppRoutingModule,
		FormsModule,
    HttpClientModule
	],
  providers: [
    AuthenticationService,
    AuthGuardService,
    UserGuardService,
    EditorGuardService,
    DataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
