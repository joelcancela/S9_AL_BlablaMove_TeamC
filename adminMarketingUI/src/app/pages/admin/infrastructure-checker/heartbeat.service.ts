import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { environment } from '../../../../environments/environment';
@Injectable({
  providedIn: 'root',
})
export class HeartbeatService {

  constructor(private http: HttpClient) {
  }

  getHeartbeats() {
    return new Promise((resolve, reject) => {
      this.http.get(environment.DASHBOARD_API_URL + '/admin/heartbeats').subscribe(result => {
        resolve(result);
      }, error => reject(error));
    });
  }
}
