import { TestBed } from '@angular/core/testing';

import { UberooStatusAPIService } from './uberoo-status-api.service';

describe('UberooStatusAPIService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UberooStatusAPIService = TestBed.get(UberooStatusAPIService);
    expect(service).toBeTruthy();
  });
});
