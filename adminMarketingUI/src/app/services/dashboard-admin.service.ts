import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ConnectionLog} from '../model/connection-log';
import {ConstantService} from '../constants/constant.service';

@Injectable({
  providedIn: 'root',
})

export class DashboardAdminService {


  API_URL = this.constant.DASHBOARD_API_URL + '/admin';
  GET_LAST_24H_CONNECTIONS = this.API_URL + '/last24Connections';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient, private constant: ConstantService) {
  }

  getLast24Connections(): Observable<ConnectionLog[]> {
    return this.http.get<ConnectionLog[]>(this.GET_LAST_24H_CONNECTIONS, this.httpOptions);
  }
}
