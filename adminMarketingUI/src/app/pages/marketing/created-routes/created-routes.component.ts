import {Component, OnDestroy, OnInit} from '@angular/core';
import {RouteCreated} from '../../../model/route-created';
import {Subject} from 'rxjs';
import {DashboardMarketingService} from '../../../services/dashboard-marketing.service';

@Component({
  selector: 'created-routes',
  templateUrl: './created-routes.component.html',
  styleUrls: ['./created-routes.component.scss']
})
export class CreatedRoutesComponent implements OnInit, OnDestroy {
  dtOptions: DataTables.Settings = {};
  routesCreated: RouteCreated[] = [];
  dtTrigger: Subject<any> = new Subject();

  constructor(private marketingService: DashboardMarketingService) {
  }

  ngOnInit() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 2
    };
    this.marketingService.getLast24hCreatedRoutes()
      .subscribe(routes => {
        this.routesCreated = routes;
        this.dtTrigger.next();
      });
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }


}
