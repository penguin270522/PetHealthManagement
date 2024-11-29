import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateProfilePetComponent } from './create-profile-pet.component';

describe('CreateProfilePetComponent', () => {
  let component: CreateProfilePetComponent;
  let fixture: ComponentFixture<CreateProfilePetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateProfilePetComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateProfilePetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
