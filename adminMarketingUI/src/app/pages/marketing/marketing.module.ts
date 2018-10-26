import { NgModule } from '@angular/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';

import { ThemeModule } from '../../@theme/theme.module';
import {HeatmapComponent} from './heatmap/heatmap.component';

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
  ],
})
export class MarketingModule { }
