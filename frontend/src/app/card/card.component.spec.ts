import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardComponent } from './card.component';
import {RouterTestingModule} from "@angular/router/testing";
import {User} from "../../objects/user";
import {City} from "../../objects/city";

describe('CardComponent', () => {
  let component: CardComponent;
  let fixture: ComponentFixture<CardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CardComponent ],
      imports: [
        RouterTestingModule
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('when role editor then edit button visible', () => {
    component.user = new User();
    component.city = new City();
    component.city.cityId = 1;
    component.city.name = 'city';
    component.city.photoLink = 'citylink';
    component.user.roles.push({name:'EDITOR'});
    fixture.detectChanges();
    let element = fixture.debugElement.nativeElement.querySelector('#edit');
    expect(element.hidden).toBeFalse();
  });

  it('when role user then edit button not visible', () => {
    component.user = new User();
    component.city = new City();
    component.city.cityId = 1;
    component.city.name = 'city';
    component.city.photoLink = 'citylink';
    component.user.roles.push({name:'USER'});
    fixture.detectChanges();
    let element = fixture.debugElement.nativeElement.querySelector('#edit');
    expect(element.hidden).toBeTrue();
  });

  it('when error during getting photo then correct information presented', () => {
    component.error = true;
    component.city = new City();
    component.city.cityId = 1;
    component.city.name = 'city';
    component.city.photoLink = 'citylink';
    fixture.detectChanges();
    let element = fixture.debugElement.nativeElement.querySelector('#imageError');
    expect(element.innerText).toBe('The image cannot be shown');
  });

  it('when when no city data then info presented', () => {
    component.user = new User();
    fixture.detectChanges();
    let element = fixture.debugElement.nativeElement.querySelector('#noData');
    expect(element.innerText).toBe('City data not found');
  });
});
