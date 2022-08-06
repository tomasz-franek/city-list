import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCityComponent } from './edit-city.component';
import {HttpClientModule} from "@angular/common/http";
import {DataService} from "../../services/data-service";
import {RouterTestingModule} from "@angular/router/testing";
import {By} from "@angular/platform-browser";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";

describe('EditCityComponent', () => {
  let component: EditCityComponent;
  let fixture: ComponentFixture<EditCityComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCityComponent ],
      providers:[
        DataService
      ],
      imports: [
        RouterTestingModule,
        FormsModule,
        HttpClientModule
      ]

    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCityComponent);
    component = fixture.componentInstance;
    router = TestBed.get(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('when city name and photo link filled up then save button active', () => {
    let element = fixture.debugElement.nativeElement.querySelector('#save');
    expect(element.disabled).toBeTrue();
    component.city.photoLink = 'a';
    component.city.name = 'a';
    fixture.detectChanges();
    element = fixture.debugElement.nativeElement.querySelector('#save');
    expect(element.disabled).toBeFalse();
  });

  it('when city name empty then save button not active', () => {
    let element = fixture.debugElement.nativeElement.querySelector('#save');
    expect(element.disabled).toBeTrue();
    component.city.photoLink = 'a';
    fixture.detectChanges();
    element = fixture.debugElement.nativeElement.querySelector('#save');
    expect(element.disabled).toBeTrue();
  });

  it('when photo link empty then save button not active', async () => {
    let element = fixture.debugElement.nativeElement.querySelector('#save');
    expect(element.disabled).toBeTrue();
    component.city.name = 'a';
    fixture.detectChanges();
    element = fixture.debugElement.nativeElement.querySelector('#save');
    expect(element.disabled).toBeTrue();
  });

  it('when click back then redirect to the dashboard', async () => {
    spyOn(router, 'navigateByUrl');
    let save = fixture.debugElement.query(By.css('#back'));
    save.triggerEventHandler('click', null);
    await fixture.whenRenderingDone();
    expect(router.navigateByUrl).toHaveBeenCalledWith('dashboard');
  });
});
