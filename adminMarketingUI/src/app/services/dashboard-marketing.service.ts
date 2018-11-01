import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CityReport} from '../model/city-report';
import {City} from '../model/city';
import {ConstantService} from '../constants/constant.service';

@Injectable({
  providedIn: 'root',
})
export class DashboardMarketingService {

  API_URL = this.constant.DASHBOARD_API_URL + '/marketing';
  GET_ACTIVE_CITIES = this.API_URL + '/cities';
  GET_MOST_ACTIVE_CITIES = this.API_URL + '/mostActiveCities';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient, private constant: ConstantService) {
  }

  getActiveCities(): Observable<City[]> {
    return this.http.get<City[]>(this.GET_ACTIVE_CITIES, this.httpOptions);
  }

  getMostActiveCities(): Observable<CityReport[]> {
    return this.http.get<CityReport[]>(this.GET_MOST_ACTIVE_CITIES, this.httpOptions);
  }
}
