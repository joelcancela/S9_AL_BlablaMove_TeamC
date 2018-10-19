import {Component, OnInit} from '@angular/core';
import {BlablaMoveStatusAPIService} from '../service/blablamove-status-api.service';

@Component({
  selector: 'app-reporting',
  templateUrl: './reporting.component.html',
  styleUrls: ['./reporting.component.css']
})
export class ReportingComponent implements OnInit {

  // ngx-charts-line-chart options
  data: object;
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
  autoScale = true;  // line, area
  constructor(private apiService: BlablaMoveStatusAPIService) {
  }

  ngOnInit() {
    this.apiService.getLastReports().subscribe(response => console.log(response));
    this.data = [
      {
        'name': 'Reports',
        'series': [
          {
            'name': '00h00',
            'value': 7
          },
          {
            'name': '01h00',
            'value': 5
          },
          {
            'name': '02h00',
            'value': 2
          },
          {
            'name': '03h00',
            'value': 0
          },
          {
            'name': '04h00',
            'value': 1
          },
          {
            'name': '05h00',
            'value': 4
          },
          {
            'name': '06h00',
            'value': 5
          },
          {
            'name': '07h00',
            'value': 2
          },
          {
            'name': '08h00',
            'value': 4
          },
          {
            'name': '09h00',
            'value': 0
          },
          {
            'name': '10h00',
            'value': 8
          },
          {
            'name': '11h00',
            'value': 1
          },
          {
            'name': '12h00',
            'value': 2
          },
          {
            'name': '13h00',
            'value': 2
          },
          {
            'name': '14h00',
            'value': 4
          },
          {
            'name': '15h00',
            'value': 5
          },
          {
            'name': '16h00',
            'value': 7
          },
          {
            'name': '17h00',
            'value': 4
          },
          {
            'name': '18h00',
            'value': 7
          },
          {
            'name': '19h00',
            'value': 0
          },
          {
            'name': '20h00',
            'value': 1
          },
          {
            'name': '21h00',
            'value': 2
          },
          {
            'name': '22h00',
            'value': 1
          },
          {
            'name': '23h00',
            'value': 3
          }
        ]
      },
      {
        'name': 'Avg.',
        'series': [
          {
            'name': '00h00',
            'value': 6
          },
          {
            'name': '12h00',
            'value': 6
          },
          {
            'name': '23h00',
            'value': 6
          }
        ]
      }
    ];
  }

  reportProblem() {
    this.apiService.postIssue();
  }

}
