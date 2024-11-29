import { TestBed } from '@angular/core/testing';

import { ServiceMedicalService } from './service-medical.service';

describe('ServiceMedicalService', () => {
  let service: ServiceMedicalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceMedicalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
