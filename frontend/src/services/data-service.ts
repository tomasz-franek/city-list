import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {City} from "../objects/city";

@Injectable({providedIn: 'root'})
export class DataService{

  constructor(private http: HttpClient) {
  }

  private static getHeader() {
    return {
      headers: new HttpHeaders({'TOKEN': localStorage.getItem('TOKEN') || '', 'Content-Type': 'application/json'}),
      withCredentials: true
    };
  }
  public getCities(page:number = 0,size:number = 8,cityName:string = ''):Observable<any> {
    if(cityName.length == 0){
      return this.http.get('city/list?page='+page+'&size='+size, DataService.getHeader());
    }else{
      return this.http.get('city/list?page='+page+'&size='+size+'&name='+cityName, DataService.getHeader());
    }
  }

  public saveCity(city:City):Observable<any>{
    return this.http.put('city/update',JSON.stringify(city),DataService.getHeader());
  }
}
