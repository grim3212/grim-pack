/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { GrimPartService } from './grim-part.service';

describe('GrimPartService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GrimPartService]
    });
  });

  it('should ...', inject([GrimPartService], (service: GrimPartService) => {
    expect(service).toBeTruthy();
  }));
});
