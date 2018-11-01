import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AppComponent} from '../app.component';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BlablaMoveStatusAPIService {

  API_URL = 'http://localhost:8080/publicstatus';
  GET_LAST_REPORTS = this.API_URL + '/last24hIncidents';
  REPORT_ISSUE = this.API_URL + '/reportIssue';
  LAST_UPDATE = this.API_URL + '/lastUpdate';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
  }

  getLastReports() {
    return this.http.get(this.GET_LAST_REPORTS, this.httpOptions);
  }

  getLastUpdate(): Observable<Date> {
    return this.http.get<Date>(this.LAST_UPDATE, this.httpOptions);
  }

  postIssue() {
    const data = {'uuid': AppComponent.uuid};
    return this.http.post(this.REPORT_ISSUE, data, this.httpOptions);
  }
}
