import { BaseDTO, UserReponse } from './../../model/interface/auth';
import { Component, inject, OnInit } from '@angular/core';
import { HomeService } from '../../services/home.service';
import { Doctor } from '../../model/class/home';
import { CommonModule } from '@angular/common';
import { AppointmentNoAccount } from '../../model/class/appointmentss';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ServiceMedicalService } from '../../services/service-medical.service';
import { ServiceMedical } from '../../model/interface/serviceMedical';
import { BaseDTOSerivce } from '../../model/interface/baseDTO';

@Component({
  selector: 'app-appointment',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './appointment.component.html',
  styleUrl: './appointment.component.scss'
})
export class AppointmentComponent implements OnInit {
  homeService = inject(HomeService);
  appointmentForm!: FormGroup;
  selectedDoctor : number = 0;
  appointmentNoAccount : AppointmentNoAccount = new AppointmentNoAccount();
  serviceMedical = inject(ServiceMedicalService)


  constructor(private fb: FormBuilder){}
  ngOnInit(): void {
    this.loadDoctor();
    this.getAllServiceMedical();
    this.appointmentForm = this.fb.group({
      nameuser: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      petName:[''],
      appointmentDate:['',Validators.required],
      doctor:['default', Validators.required],
      serviceMedical:['default', Validators.required],
      message:['']
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

  role: String = "ROLE_DOCTOR"
  doctorArray : Doctor[] =[];
  loadDoctor(){
    this.homeService.showDoctor(this.role).subscribe((any: UserReponse)=>{
      if(any.baseDTO.result){
        this.doctorArray = any.userList;
      }
    });
  }

  onSubmit(){
    if(this.appointmentForm.valid){
      debugger;
      this.appointmentNoAccount.username = this.appointmentForm.value.nameuser;
      this.appointmentNoAccount.numberPhone = this.appointmentForm.value.phone;
      this.appointmentNoAccount.namePet = this.appointmentForm.value.petName;
      this.appointmentNoAccount.dateTime = this.appointmentForm.value.appointmentDate;
      this.appointmentNoAccount.message = this.appointmentForm.value.message;
      this.appointmentNoAccount.doctorId = this.appointmentForm.value.doctor;
      this.appointmentNoAccount.serviceId = this.appointmentForm.value.serviceMedical;
      this.homeService.creatAppointmentForUserNoAccount(this.appointmentNoAccount).subscribe((any:BaseDTO)=>{
        if(any.result){
          console.log(any.message)
          this.showSuccessMessageInvocie();
        }else{
          alert("errors")
        }
      })
    }
  }

  progressWidth: number = 0;
  showSuccessInvoice : boolean = false;
  showSuccessMessageInvocie() {
    this.showSuccessInvoice = true;
    this.progressWidth = 0;
    const interval = setInterval(() => {
      if (this.progressWidth < 100) {
        this.progressWidth += 2;
      } else {
        clearInterval(interval);
        this.showSuccessInvoice = false;
      }
    }, 100);
  }
}
