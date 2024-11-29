import { TestBed } from '@angular/core/testing';

import { TypeQuestionService } from './type-question.service';

describe('TypeQuestionService', () => {
  let service: TypeQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeQuestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
