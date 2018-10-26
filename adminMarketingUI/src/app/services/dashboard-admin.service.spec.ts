import { TestBed, inject } from '@angular/core/testing';

import { DashboardAdminService } from './dashboard-admin.service';

describe('DashboardAdminService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DashboardAdminService]
    });
  });

  it('should be created', inject([DashboardAdminService], (service: DashboardAdminService) => {
    expect(service).toBeTruthy();
  }));
});
