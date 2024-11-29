import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Pet } from '../../../../model/class/pet';
import { PetService } from '../../../../services/pet.service';
import { CommonModule } from '@angular/common';
import { PetProfile } from '../../../../model/interface/basePet';
import { BaseDTO } from '../../../../model/interface/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-profile-pet',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './create-profile-pet.component.html',
  styleUrl: './create-profile-pet.component.scss'
})
export class CreateProfilePetComponent implements OnInit {
  ngOnInit(): void {
    this.createFormPet = this.fb.group({
      name:['', Validators.required],
      typePet: ['', Validators.required],
      genderPet:['', Validators.required],
      birthDay:['', Validators.required],
      adoptive:['',Validators.required],
      weight:['',Validators.required],
      furColor:['',Validators.required],
      crystal:['',Validators.required]
    })
  }
  constructor(private fb: FormBuilder, private router: Router,private cdRef: ChangeDetectorRef){

  }
  showNotification = false;
  newPet: Pet = new Pet();
  petService = inject(PetService);
  createFormPet!: FormGroup;
  selectedSex: string = '';
  petImagePreview: string | ArrayBuffer = 'https://img.freepik.com/free-vector/pet-logo-design-paw-vector-animal-shop-business_53876-136741.jpg';
  petImageFile: File | null = null;
  createPet : boolean = false;

  selectSex(sex: string) {
    this.selectedSex = sex;
    this.createFormPet.get('genderPet')?.setValue(sex === 'duc' ? 'DUC' : 'CAI');
  }
  petId: number | undefined;
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.petImageFile = input.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const result = e.target?.result;
        if (typeof result === 'string' || result instanceof ArrayBuffer) {
          this.petImagePreview = result;
        }
      };
      reader.readAsDataURL(input.files[0]);
    }
    event.preventDefault();
  }
  triggerFileInput(): void {
    const fileInput = document.getElementById('file-input') as HTMLInputElement;
    fileInput.click();
  }
  tradeCreateProfilePet(){
    this.router.navigate(['/profile-user/pets']);
  }
  showNotion(){
    this.showNotification = true;
    setTimeout(() => {
      this.showNotification = false;
      this.tradeCreateProfilePet();
    }, 3000);
  }
  creatProfilePet() {
      debugger;
      this.newPet = { ...this.createFormPet.value };
      this.petService.createProfilePet(this.newPet).subscribe((responseProfile: PetProfile) => {
        if (responseProfile.results) {
          console.log(responseProfile.message);
          if (this.petImageFile) {
            this.petService.createImagePet(responseProfile.petId, this.petImageFile).subscribe((responseImage: BaseDTO) => {
              if (responseImage.result) {
                console.log(responseImage.message);
              } else {
                console.log(responseImage.message);
              }
            });
          }
          this,this.showNotion();
        } else {
          console.log(responseProfile.message);
        }
      });
  }
}
