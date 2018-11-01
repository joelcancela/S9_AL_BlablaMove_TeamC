import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ConstantService {
  DASHBOARD_API_URL = 'http://localhost:8080';

  constructor() {
  }
}
