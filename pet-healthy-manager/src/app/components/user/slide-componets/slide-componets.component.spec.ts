import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlideComponetsComponent } from './slide-componets.component';

describe('SlideComponetsComponent', () => {
  let component: SlideComponetsComponent;
  let fixture: ComponentFixture<SlideComponetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SlideComponetsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SlideComponetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
