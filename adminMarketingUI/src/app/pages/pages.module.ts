import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { PagesRoutingModule } from './pages-routing.module';
import { ThemeModule } from '../@theme/theme.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import {MarketingComponent} from './marketing/marketing.component';
import {AdminComponent} from './admin/admin.component';
import {MarketingModule} from './marketing/marketing.module';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import { InfrastructureCheckerComponent } from './admin/infrastructure-checker/infrastructure-checker.component';


const PAGES_COMPONENTS = [
  PagesComponent,
];

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    DashboardModule,
    MiscellaneousModule,
    MarketingModule,
    NgxChartsModule,
  ],
  declarations: [
    ...PAGES_COMPONENTS,
    MarketingComponent,
    AdminComponent,
    InfrastructureCheckerComponent,
  ],
})
export class PagesModule {
}
