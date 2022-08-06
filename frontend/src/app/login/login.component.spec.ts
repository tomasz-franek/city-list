import {LoginComponent} from "./login.component";
import {async, ComponentFixture, TestBed} from "@angular/core/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {AuthenticationService} from "../../services/authentication-service";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        LoginComponent
      ],
      providers: [
        AuthenticationService
      ],
      imports: [
        FormsModule,
        RouterTestingModule,
        HttpClientModule
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it('exists Login field', () => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('input[name="username"]').hidden).toBeFalse();
  });

  it('exists Password field', () => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('input[name="password"]').hidden).toBeFalse();
  });

  it('exists Button login', () => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('button[name="action"]').hidden).toBeFalse();
  });


  it('when user or password not selected then login button not active', () => {
    let login = fixture.debugElement.nativeElement.querySelector('#login');
    expect(login.disabled).toBeTrue();
    component.username = 'a';
    fixture.detectChanges();
    login = fixture.debugElement.nativeElement.querySelector('#login');
    expect(login.disabled).toBeTrue();
    component.password = 'a';
    fixture.detectChanges();
    login = fixture.debugElement.nativeElement.querySelector('#login');
    expect(login.disabled).toBeFalse();

  });


});
