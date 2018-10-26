import { TestBed, inject } from '@angular/core/testing';

import { OpenstreetmapService } from './openstreetmap.service';

describe('OpenstreetmapService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OpenstreetmapService]
    });
  });

  it('should be created', inject([OpenstreetmapService], (service: OpenstreetmapService) => {
    expect(service).toBeTruthy();
  }));
});
