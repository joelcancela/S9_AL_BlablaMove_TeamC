import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {RouteCreated} from '../../../model/route-created';
import {Subject} from 'rxjs';
import {DashboardMarketingService} from '../../../services/dashboard-marketing.service';
import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-bs4';

@Component({
  selector: 'created-routes',
  templateUrl: './created-routes.component.html',
  styleUrls: ['./created-routes.component.scss']
})
export class CreatedRoutesComponent implements OnInit, OnDestroy {
  routesCreated: RouteCreated[] = [];
  dataTable: any;

  constructor(private marketingService: DashboardMarketingService, private chRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.marketingService.getLast24hCreatedRoutes()
      .subscribe(routes => {
        this.routesCreated = routes;
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      }, error => {
        this.routesCreated.push(new RouteCreated('Nice', '404', 'Antibes', new Date()));
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      });
  }

  ngOnDestroy(): void {
  }


}
