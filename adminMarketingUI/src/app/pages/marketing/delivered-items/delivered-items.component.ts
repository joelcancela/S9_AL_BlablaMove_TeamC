import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DeliveryIssue } from "../../../model/delivery-issue";
import { DashboardMarketingService } from "../../../services/dashboard-marketing.service";
import { DeliveredItem } from "../../../model/delivered-item";
import * as $ from "jquery";

@Component({
  selector: 'delivered-items',
  templateUrl: './delivered-items.component.html',
  styleUrls: ['./delivered-items.component.scss']
})
export class DeliveredItemsComponent implements OnInit {
  deliveredItems: DeliveredItem[] = [];
  dataTable: any;

  constructor(private marketingService: DashboardMarketingService, private chRef: ChangeDetectorRef) {
    this.marketingService.getLast24hDeliveredItems()
      .subscribe(items => {
        this.deliveredItems = items;
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      }, error => {
        this.deliveredItems.push(new DeliveredItem('Nothing', '404', new Date()));
        this.chRef.detectChanges();
        const table: any = $('#table');
        this.dataTable = table.DataTable();
      });
  }

  ngOnInit() {
  }

}
