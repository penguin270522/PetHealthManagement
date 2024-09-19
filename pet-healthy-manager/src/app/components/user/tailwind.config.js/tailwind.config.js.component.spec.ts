import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TailwindConfigJsComponent } from './tailwind.config.js.component';

describe('TailwindConfigJsComponent', () => {
  let component: TailwindConfigJsComponent;
  let fixture: ComponentFixture<TailwindConfigJsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TailwindConfigJsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TailwindConfigJsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
