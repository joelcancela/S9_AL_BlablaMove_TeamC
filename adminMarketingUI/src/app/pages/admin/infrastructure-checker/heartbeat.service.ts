import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConstantService} from '../../../constants/constant.service';

@Injectable({
  providedIn: 'root'
})
export class HeartbeatService {

  constructor(private http: HttpClient, private constantService: ConstantService) {
  }

  getHeartbeats() {
    return new Promise((resolve, reject) => {
      this.http.get(this.constantService.DASHBOARD_API_URL + '/admin/heartbeats').subscribe(result => {
        resolve(result);
      }, error => reject(error));
    });
  }
}
