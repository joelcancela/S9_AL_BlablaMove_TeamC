import { TestBed } from '@angular/core/testing';

import { BlablaMoveStatusAPIService } from './BlablaMove-status-api.service';

describe('BlablaMoveStatusAPIService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BlablaMoveStatusAPIService = TestBed.get(BlablaMoveStatusAPIService);
    expect(service).toBeTruthy();
  });
});
