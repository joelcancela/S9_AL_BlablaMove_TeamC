import { NgModule } from '@angular/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';

import { ThemeModule } from '../../@theme/theme.module';
import {HeatmapComponent} from './heatmap/heatmap.component';
import { CreatedRoutesComponent } from './created-routes/created-routes.component';
import { CanceledRoutesComponent } from './canceled-routes/canceled-routes.component';
import { DeliveredItemsComponent } from './delivered-items/delivered-items.component';
import { DeliveryIssuesComponent } from './delivery-issues/delivery-issues.component';

@NgModule({
  imports: [
    ThemeModule,
    LeafletModule.forRoot(),
  ],
  exports: [
    HeatmapComponent,
  ],
  declarations: [
    HeatmapComponent,
    CreatedRoutesComponent,
    CanceledRoutesComponent,
    DeliveredItemsComponent,
    DeliveryIssuesComponent,
  ],
})
export class MarketingModule { }
