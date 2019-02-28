import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import * as $ from 'jquery';
import {RouteCreated} from '../../../model/route-created';
import {DashboardMarketingService} from '../../../services/dashboard-marketing.service';
import {DeliveryIssue} from '../../../model/delivery-issue';

@Component({
  selector: 'delivery-issues',
  templateUrl: './delivery-issues.component.html',
  styleUrls: ['./delivery-issues.component.scss']
})
export class DeliveryIssuesComponent implements OnInit {
  deliveryIssues: DeliveryIssue[] = [];
  dataTable: any;

  constructor(private marketingService: DashboardMarketingService, private chRef: ChangeDetectorRef) {
  }


  ngOnInit() {
    this.marketingService.getLast24hDeliveryIssues()
      .subscribe(routes => {
        this.deliveryIssues = routes;
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      }, error => {
        this.deliveryIssues.push(new DeliveryIssue('Lost', '404', new Date()));
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      });
  }

}
