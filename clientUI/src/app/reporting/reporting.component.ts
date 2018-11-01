import {Component, OnInit} from '@angular/core';
import {BlablaMoveStatusAPIService} from '../service/blablamove-status-api.service';
import {forEach} from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-reporting',
  templateUrl: './reporting.component.html',
  styleUrls: ['./reporting.component.css']
})
export class ReportingComponent implements OnInit {

  timeOptions = {hour: '2-digit', minute: '2-digit'};
  // ngx-charts-line-chart options
  data = [
    {
      'name': 'Reports',
      'series': [
        {
          'name': '00h00',
          'value': 7
        }
      ]
    }];
  view: any[] = [700, 400];
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = false;
  showXAxisLabel = false;
  showYAxisLabel = true;
  yAxisLabel = 'Reported problems';
  timeline = true;
  yScaleMax = 20;
  yScaleMin = 0;
  colorScheme = {
    domain: ['#001acc', '#a10a28']
  };
  autoScale = true;
  lastHourProblems = 0;
  lastUpdate = null;

  // line, area
  constructor(private apiService: BlablaMoveStatusAPIService) {
  }

  ngOnInit() {
    this.apiService.getLastReports().subscribe(response => this.adaptData(response));
    this.apiService.getLastUpdate().subscribe(response => this.lastUpdate = this.formatDate(response));
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
    const reFirst = new RegExp('first', 'g');
    const reSecond = new RegExp('second', 'g');
    const adaptedResponse = JSON.stringify(response).replace(reFirst, 'name').replace(reSecond, 'value');
    const adaptedObject = JSON.parse(adaptedResponse);
    adaptedObject.reverse();
    adaptedObject.map(obj => obj.name = this.formatDate(obj.name));
    const avg = Math.round(adaptedObject.reduce((total, obj) => total + obj.value, 0) / adaptedObject.length);
    this.lastHourProblems = adaptedObject[adaptedObject.length - 1].value;
    this.data = [
      {
        'name': 'Reports',
        'series': adaptedObject
      },
      {
        'name': 'Average',
        'series': [{
          'name': adaptedObject[0].name,
          'value': avg
        }, {
          'name': adaptedObject[adaptedObject.length - 1].name,
          'value': avg
        }]
      }];
  }

  reportProblem() {
    this.apiService.postIssue().subscribe(response => console.log(response));
  }

}
