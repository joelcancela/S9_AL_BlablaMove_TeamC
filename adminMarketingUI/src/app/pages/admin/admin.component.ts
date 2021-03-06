import { Component, OnInit } from '@angular/core';
import { DashboardAdminService } from '../../services/dashboard-admin.service';

@Component({
  selector: 'ngx-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {

  timeOptions = { hour: '2-digit', minute: '2-digit' };
  // ngx-charts-line-chart options
  data = [
    {
      'name': 'Connections',
      'series': [
        {
          'name': '00h00',
          'value': 7,
        },
      ],
    }];
  view: any[] = [700, 400];
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = false;
  showXAxisLabel = false;
  showYAxisLabel = true;
  yAxisLabel = 'Users connections';
  timeline = true;
  yScaleMax = 500;
  yScaleMin = 0;
  colorScheme = {
    domain: ['#001acc', '#a10a28'],
  };
  autoScale = true;

  constructor(private dashboardAdminService: DashboardAdminService) {
  }

  ngOnInit() {
    this.dashboardAdminService.getLast24Connections().subscribe(response => this.adaptData(response));
    setInterval(function () {
      document.getElementById("chartdiv").scrollLeft = 500;
    }, 500);
  }

  formatDate(dateString) {
    const date = new Date(dateString);
    const yesterday = new Date(Date.now() - 86400000);
    if (date.getDate() === yesterday.getDate()) {
      return 'Hier, ' + date.toLocaleTimeString('fr-FR', this.timeOptions);
    } else {
      return date.toLocaleTimeString('fr-FR', this.timeOptions);
    }
  }

  adaptData(response) {
    console.log(response);
    const reFirst = new RegExp('localDateTime', 'g');
    const reSecond = new RegExp('totalUsersConnected', 'g');
    const adaptedResponse = JSON.stringify(response).replace(reFirst, 'name').replace(reSecond, 'value');
    const adaptedObject = JSON.parse(adaptedResponse);
    adaptedObject.reverse();
    adaptedObject.map(obj => obj.name = this.formatDate(obj.name));
    const avg = Math.round(adaptedObject.reduce((total, obj) => total + obj.value, 0) / adaptedObject.length);
    const max = adaptedObject.reduce((max, n) => Math.max(max, n.value), adaptedObject[0].value);
    this.yScaleMax = ((max / (10 * Math.log10(max))) + 1) * (10 * Math.log10(max));
    this.data = [
      {
        'name': 'Connections',
        'series': adaptedObject,
      },
      {
        'name': 'Average',
        'series': [{
          'name': adaptedObject[0].name,
          'value': avg,
        }, {
          'name': adaptedObject[adaptedObject.length - 1].name,
          'value': avg,
        }]
      }];
  }

}
