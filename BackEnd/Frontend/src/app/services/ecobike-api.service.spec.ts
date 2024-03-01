import { TestBed } from '@angular/core/testing';

import { EcobikeApiService } from './ecobike-api.service';

describe('EcobikeApiService', () => {
  let service: EcobikeApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EcobikeApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
