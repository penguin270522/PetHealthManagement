import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomepageDoctorComponent } from './homepage-doctor.component';

describe('HomepageDoctorComponent', () => {
  let component: HomepageDoctorComponent;
  let fixture: ComponentFixture<HomepageDoctorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomepageDoctorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomepageDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
