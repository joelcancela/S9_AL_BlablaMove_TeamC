import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DashboardApiService {

  constructor() {
  }

  showMessage() {
    console.log('Wow');
  }
}
