import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DashboardComponent} from './dashboard.component';
import {RouterTestingModule} from "@angular/router/testing";
import {DataService} from "../../services/data-service";
import {HttpClientModule} from "@angular/common/http";
import {AuthenticationService} from "../../services/authentication-service";
import {Router} from "@angular/router";
import {By} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardComponent],
      imports: [
        RouterTestingModule,
        FormsModule,
        HttpClientModule
      ],
      providers: [
        DataService,
        AuthenticationService
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    router = TestBed.get(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('when click logout then redirect to login', async () => {
    fixture.detectChanges();
    spyOn(router, 'navigateByUrl');
    let addButton = fixture.debugElement.query(By.css('#logout'));
    addButton.triggerEventHandler('click', null);
    await fixture.whenRenderingDone();
    expect(router.navigateByUrl).toHaveBeenCalledWith('login');
  });

  it('when page 0 then prev page button disabled', async () => {
    component.page = 0;
    component.totalPages = 10;
    await   fixture.detectChanges();
    let element = fixture.debugElement.nativeElement.querySelector('#prev');
    expect(element.classList).toContain('disabled');
  });

  it('when last page then next page button disabled', async () => {
    component.page = 9;
    component.totalPages = 10;
    await   fixture.detectChanges();
    let element = fixture.debugElement.nativeElement.querySelector('#next');
    expect(element.classList).toContain('disabled');
  });
});
