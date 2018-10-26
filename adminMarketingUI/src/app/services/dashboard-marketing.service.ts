import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class DashboardMarketingService {

  API_URL = 'http://localhost:8080/marketing';
  GET_ACTIVE_CITIES = this.API_URL + '/cities';
  GET_MOST_ACTIVE_CITIES = this.API_URL + '/mostActiveCities';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {
  }

  getActiveCities() {
    return this.http.get(this.GET_ACTIVE_CITIES, this.httpOptions);
  }

  getMostActiveCities() {
    return this.http.get(this.GET_MOST_ACTIVE_CITIES, this.httpOptions);
  }
}
