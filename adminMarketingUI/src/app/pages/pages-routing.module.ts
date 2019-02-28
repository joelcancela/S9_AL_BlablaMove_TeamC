import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

import {PagesComponent} from './pages.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MarketingComponent} from './marketing/marketing.component';
import {AdminComponent} from './admin/admin.component';
import {CreatedRoutesComponent} from './marketing/created-routes/created-routes.component';
import {CanceledRoutesComponent} from './marketing/canceled-routes/canceled-routes.component';
import {DeliveredItemsComponent} from './marketing/delivered-items/delivered-items.component';
import {DeliveryIssuesComponent} from './marketing/delivery-issues/delivery-issues.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      component: DashboardComponent,
    },
    {
      path: 'marketing-most-active-cities',
      component: MarketingComponent,
    },
    {
      path: 'marketing-created-routes',
      component: CreatedRoutesComponent,
    },
    {
      path: 'marketing-canceled-routes',
      component: CanceledRoutesComponent,
    },
    {
      path: 'marketing-delivered-items',
      component: DeliveredItemsComponent,
    },
    {
      path: 'marketing-delivery-issues',
      component: DeliveryIssuesComponent,
    },
    {
      path: 'administration',
      component: AdminComponent,
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
