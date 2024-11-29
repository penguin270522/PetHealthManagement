import { ServiceMedical } from './../../../../model/interface/serviceMedical';
import { Pet, PetDetails } from './../../../../model/class/pet';
import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { PetService } from '../../../../services/pet.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BaseDTO, UserReponse } from '../../../../model/interface/auth';
import { Doctor } from '../../../../model/class/home';
import { HomeService } from '../../../../services/home.service';
import { AppointmentAccount, AppointmentNoAccount } from '../../../../model/class/appointmentss';
import { ServiceMedicalService } from '../../../../services/service-medical.service';
import { BaseDTOSerivce } from '../../../../model/interface/baseDTO';

@Component({
  selector: 'app-pet-details',
  standalone: true,
  imports: [IonicModule,CommonModule,ReactiveFormsModule],
  templateUrl: './pet-details.component.html',
  styleUrl: './pet-details.component.scss'
})
export class PetDetailsComponent implements OnInit {
  petService = inject(PetService);
  homeService = inject(HomeService);
  serviceMedical = inject(ServiceMedicalService)
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.petId = +params['id'];
      this.getPetDetails(this.petId);
    });
    debugger;
    this.loadDoctor();
    this.getAllServiceMedical();
    this.appointmentForm = this.fb.group({
      appointmentDate:['',Validators.required],
      doctor:['default', Validators.required],
      serviceMedical:['default', Validators.required],
      message:['']
    })
    this.updatePetForm = this.fb.group({
      name:[''],
      genderPet:[''],
      birthDay:[''],
      adoptive:[''],
      furColor:[''],
      crystal:['']
    })
  }

  appointmentAccount : AppointmentAccount = new AppointmentAccount();
  showAppointment: boolean = false;
  petId!: number;
  constructor(private route: ActivatedRoute,private fb: FormBuilder) {

  }
  petDetails!: PetDetails;
  appointment : boolean = true;
  mediacalReport: boolean = false;
  appointmentForm!: FormGroup;
  selectedDoctor : number = 0;
  totalAppointment: number = 0;
  totalMedicalReport : number = 0;
  updatePetForm!: FormGroup;

  getPetDetails(id: number){
    debugger;
    this.petService.getPetWithId(id).subscribe((any: PetDetails)=>{
      this.petDetails = any;
      this.petImagePreview = any.urlImagePet.toString();
      this.totalAppointment = any.totalAppointment;
      this.totalMedicalReport = any.totalMedicalReport;
      console.log(this.petDetails);
    })
  }

  serviceMedicals!: ServiceMedical[];
  getAllServiceMedical(){
    this.serviceMedical.getAllServiceMedical().subscribe((any: BaseDTOSerivce)=>{
      if(any.result){
        this.serviceMedicals = any.results
      }
    })
  }

  createAppointment(){
    this.showAppointment = !this.showAppointment;
    console.log("showAppointment is now:", this.showAppointment);
  }

  role: String = "ROLE_DOCTOR"
  doctorArray : Doctor[] =[];
  loadDoctor(){
    debugger
    this.homeService.showDoctor(this.role).subscribe((any: UserReponse)=>{
      if(any.baseDTO.result){
        this.doctorArray = any.userList;
      }
    });
  }

  onSubmit(){
    if(this.appointmentForm.valid){
      debugger;
      this.appointmentAccount.dateTime = this.appointmentForm.value.appointmentDate;
      this.appointmentAccount.message = this.appointmentForm.value.message;
      this.appointmentAccount.doctorId = this.appointmentForm.value.doctor;
      this.appointmentAccount.serviceId = this.appointmentForm.value.serviceMedical;
      this.appointmentAccount.namePet = this.petDetails.namePet;
      this.appointmentAccount.username = localStorage.getItem('fullName');
      this.appointmentAccount.petId = this.petId;
      this.homeService.creatAppointmentForUserAccount(this.appointmentAccount).subscribe((any:BaseDTO)=>{
        if(any.result){
          this.getPetDetails(this.petId)
          this.showAppointment = false;
        }else{
          alert("errors")
        }
      })
    }
  }
  switchAppointmentAndMedicalReport(){
    this.appointment = !this.appointment;
    this.mediacalReport = !this.mediacalReport;
  }
  showBodyAppointment(){
    return !this.showNotionAppointment()
  }
  showNotionAppointment(){
    if(this.totalAppointment == 0){
      return true;
    }else{
      return false;
    }
  }
  showBodyMedicalReport(){
    return !this.showNotionMedicalReport
  }
  showNotionMedicalReport(){
    if(this.totalMedicalReport == 0){
      return true;
    }else{
      return false;
    }
  }
  turnOnMedicalReport(){
    this.mediacalReport = true;
    this.appointment = false;
  }
  turnOnAppointment(){
    this.appointment = true;
    this.mediacalReport = false;
  }

  petImagePreview: string = "";
  petImageFile: File | null = null;
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.petImageFile = input.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const result = e.target?.result;
        if (typeof result === 'string') {
          this.petImagePreview = result;
        }
      };
      reader.readAsDataURL(input.files[0]);
    }
    event.preventDefault();
  }

  triggerFileInput(): void {
    const fileInput = document.getElementById('file-input') as HTMLInputElement;
    if (fileInput) {
      fileInput.click();
    }
  }
  choosePicture = false;
  closeChosePicture(){
    this.choosePicture = !this.choosePicture;
  }

  updateImageProfilePet(){
    debugger;
    if(this.petImageFile){
      this.petService.deletePetWithId(this.petId).subscribe((any:BaseDTO)=> {
        if(this.petImageFile){
          if(any.result){
            this.petService.createImagePet(this.petId, this.petImageFile).subscribe((responseImage: BaseDTO) => {
              if (responseImage.result) {
                this.getPetDetails(this.petId);
                this.choosePicture = false;
                this.showNotionSuccess();
                console.log(responseImage.message);
              } else {
                console.log(responseImage.message);
              }
            });
          }
        }
      })
    }
  }

  showSuccess:boolean = false;
  chooseCloseSuccess(){
    this.showSuccess = !this.showSuccess
  }
  showNotionSuccess(){
    this.showSuccess = true;
    setTimeout(() => {
      this.showSuccess = false;
    }, 3000);
  }

  isDisabled: boolean = true;
  showChoice:boolean = false;
  showCrystal: boolean = true;
  showEditCrystal: boolean = false;
  toggleEdit(): void {
    this.isDisabled = !this.isDisabled;
    this.showChoice = !this.showChoice;
    this.showCrystal = !this.showCrystal;
    this.showEditCrystal = !this.showEditCrystal;
  }

  newPet : Pet = new Pet();
  showSuccessUpdate:boolean = false;
  chooseCloseSuccessUpdate(){
    this.showSuccess = !this.showSuccess
  }
  showNotionSuccessUpdate(){
    this.showSuccessUpdate = true;
    setTimeout(() => {
      this.showSuccessUpdate = false;
    }, 3000);
  }
  updatePet(){
    debugger;
    if(this.updatePetForm.valid){
      this.newPet = { ...this.updatePetForm.value};
      this.petService.uploadPet(this.petId,this.newPet).subscribe((any:BaseDTO)=>{
        if(any.result){
          this.getPetDetails(this.petId);
          this.showNotionSuccessUpdate();
          this.toggleEdit();
        }else{
          console.log(any.message);
        }
      })
    }else{
      console.log("Loi form")
    }
  }
}
