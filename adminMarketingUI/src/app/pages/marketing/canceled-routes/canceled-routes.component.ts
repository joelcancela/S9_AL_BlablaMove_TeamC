import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {RouteCreated} from '../../../model/route-created';
import {DashboardMarketingService} from '../../../services/dashboard-marketing.service';
import {RouteCanceled} from '../../../model/route-canceled';
import * as $ from 'jquery';

@Component({
  selector: 'canceled-routes',
  templateUrl: './canceled-routes.component.html',
  styleUrls: ['./canceled-routes.component.scss']
})
export class CanceledRoutesComponent implements OnInit {

  routesCanceled: RouteCanceled[] = [];
  dataTable: any;

  constructor(private marketingService: DashboardMarketingService, private chRef: ChangeDetectorRef) {
    this.marketingService.getLast24hCanceledRoutes()
      .subscribe(routes => {
        this.routesCanceled = routes;
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      }, error => {
        this.routesCanceled.push(new RouteCanceled('404', new Date()));
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      });
  }

  ngOnInit() {
  }

}
