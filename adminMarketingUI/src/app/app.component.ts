import {Component} from '@angular/core';
import {DashboardApiService} from './service/dashboard-api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'BlablaMove\'s Admin Dashboard';

  constructor(private dashboardService: DashboardApiService) {
    this.dashboardService.showMessage();
  }
}
