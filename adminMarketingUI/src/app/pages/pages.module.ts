import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { PagesRoutingModule } from './pages-routing.module';
import { ThemeModule } from '../@theme/theme.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import {MarketingComponent} from './marketing/marketing.component';
import {AdminComponent} from './admin/admin.component';
import {MarketingModule} from './marketing/marketing.module';


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
  ],
  declarations: [
    ...PAGES_COMPONENTS,
    MarketingComponent,
    AdminComponent,
  ],
})
export class PagesModule {
}