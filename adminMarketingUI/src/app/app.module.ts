import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MarketingComponent } from './marketing/marketing.component';

import {RouterModule, Routes} from '@angular/router';
import { AdminComponent } from './admin/admin.component';

const appRoutes: Routes= [
  { path: '', component: AppComponent},
  { path: 'marketing', component: MarketingComponent},
  { path: 'admin', component: AdminComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    MarketingComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true}
    )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
