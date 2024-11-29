import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { Pet, PetWithStatus } from '../../../model/class/pet';
import { PetProfile } from '../../../model/interface/basePet';
import { BaseDTO } from '../../../model/interface/auth';
import { APIResponseModel } from '../../../model/interface/appointment';
import { PetService } from '../../../services/pet.service';

@Component({
  selector: 'app-pet',
  standalone: true,
  imports: [IonicModule, CommonModule,RouterModule,ReactiveFormsModule],
  templateUrl: './pet.component.html',
  styleUrls: ['./pet.component.scss']
})
export class PetComponent implements OnInit{
  cdRef: any;
  ngOnInit(): void {
    debugger
    this.viewAllPet()
  }

  petService = inject(PetService)
  constructor(private fb: FormBuilder, private router: Router){
  }
  createPet(){
    this.router.navigate(['/profile-user/pet/create-pet']);
  }
  limit : number = 12;
  page : number = 1;
  userId = localStorage.getItem('user_id')
  pet : Pet [] = [];
  totalItem: number = 0;
  totalPage: number = 0;
  onLoadingPet(){
    if(this.userId){
      this.petService.getAllPet(this.userId,this.limit,this.page).subscribe((any:APIResponseModel)=>{
        if(any.result){
          this.pet = any.simpleResponese.results;
          this.totalItem = any.simpleResponese.totalItem;
          this.totalPage = any.simpleResponese.totalPage;
        }else{
          console.log(any.message)
        }
      })
    }
  }

  petHaveAppointment(){

  }
  shouldShowApp() {
    return !this.router.url.includes('/pets/pet') && !this.router.url.endsWith('/create-pet');
  }
  showHeaderApp(){
    return !this.router.url.includes('/pets/pet')
  }
  viewPetDetail(petId: number){
    this.router.navigate([''])
  }
  getAllPet:boolean = false;
  getPetWithPetHaveAppointment:boolean = true;
  getPetWithPetHaveMedicalReport:boolean = false;
  notion:boolean = false;
  viewAllPet(){
    this.getAllPet = true;
    this.getPetWithPetHaveAppointment = false;
    this.getPetWithPetHaveMedicalReport = false;
    this.onLoadingPet();
  }
  viewPetHaveAppointmentActive(){
    debugger;
    this.notion = false;
    this.getAllPet = false;
    this.getPetWithPetHaveAppointment = true;
    this.getPetWithPetHaveMedicalReport = false;
    if(this.userId){
      this.petService.getPetWithStatus(this.userId,this.limit,this.page).subscribe((any:PetWithStatus)=>{
        if(any.results){
          this.pet = any.petList;
          if(any.totalAppointmentActive <= 0){
            this.notion = true;
          }
        }
      })
    }
  }
  viewPetHaveMedicalReport(){
    debugger
    this.getAllPet = false;
    this.notion = false;
    this.getPetWithPetHaveAppointment = false;
    this.getPetWithPetHaveMedicalReport = true;
    if(this.userId){
      this.petService.getPetWithMedical(this.userId,this.limit,this.page).subscribe((any:PetWithStatus)=>{
        if(any.results){
          this.pet = any.petList;
          if(any.totalMedicalActive <= 0){
            this.notion = true;
          }
        }
      })
    }
  }
}
