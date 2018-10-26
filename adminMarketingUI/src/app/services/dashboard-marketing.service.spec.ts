import { TestBed, inject } from '@angular/core/testing';

import { DashboardMarketingService } from './dashboard-marketing.service';

describe('DashboardMarketingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DashboardMarketingService]
    });
  });

  it('should be created', inject([DashboardMarketingService], (service: DashboardMarketingService) => {
    expect(service).toBeTruthy();
  }));
});
