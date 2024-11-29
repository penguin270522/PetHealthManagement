import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserScurityComponent } from './user-scurity.component';

describe('UserScurityComponent', () => {
  let component: UserScurityComponent;
  let fixture: ComponentFixture<UserScurityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserScurityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserScurityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
