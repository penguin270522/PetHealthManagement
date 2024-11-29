import { QuestionOutput, QuestionPage } from './../../../../model/interface/question';
import { AppointmentDoctorOutputs } from './../../../../model/interface/medicalReport';
import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {  Router, RouterLink, RouterOutlet } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { AuthService } from '../../../../services/auth/auth.service';
import { ProfileService } from '../../../../services/profile.service';
import { DoctorAppointmentService } from '../../../../services/doctor-appointment.service';
import { AppointmentToday } from '../../../../model/interface/medicalReport';
import { QuestionService } from '../../../../services/question.service';


@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule,FormsModule,IonicModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss',

})
export class HomepageComponent implements OnInit{
  avatar: string = localStorage.getItem('avatarUser')?? ''
  fullName: string = localStorage.getItem('fullNameUser') ?? ''
  appointmentDetailToday!:AppointmentDoctorOutputs;
  authService = inject(AuthService)
  doctorService = inject(DoctorAppointmentService)
  userProfileService = inject(ProfileService)
  questionService = inject(QuestionService)
  constructor(private router: Router) {}
  ngOnInit(): void {
    this.onLoadingAppointment();
    this.onLoadingQuestion();
    debugger;
    const storedAvatar = localStorage.getItem('avatarProfileUser');
    const storedFullName = localStorage.getItem('fullNameProfile');

    if (storedAvatar) {
      this.avatar = storedAvatar;
    }
    if (storedFullName) {
      this.fullName = storedFullName;
    }
    this.userProfileService.currentAvatar.subscribe(avatar => {
      if (avatar) {
        this.avatar = avatar;
        localStorage.setItem('avatarProfileUser', avatar);
      }
    });

    this.userProfileService.currentFullName.subscribe(fullName => {
      if (fullName) {
        this.fullName = fullName;
        localStorage.setItem('fullNameProfile', fullName);
      }
    });
  }
  dateNow: string | undefined ;
  getFormattedDate() {
    const today = new Date();
    this.dateNow = today.getFullYear() + '-' + (today.getMonth() + 1).toString().padStart(2, '0') + '-' + today.getDate().toString().padStart(2, '0');
  }
  appointmentDoctorOutput: AppointmentDoctorOutputs [] = [];
  totalAppointToday: number = 0;

  showNotionAppointmentToday() {
    return this.totalAppointToday === 0;
  }

  showListAppointmentToday() {
    return !this.showNotionAppointmentToday();
  }

  onLoadingAppointment(){
    this.getFormattedDate();
    if(this.dateNow){
      const doctorId = localStorage.getItem('user_id');
      if(doctorId){
        this.doctorService.getAllAppointmentToday(doctorId,this.dateNow).subscribe((any:AppointmentToday)=>{
          if(any.results){
            this.appointmentDoctorOutput = any.appointmentDoctorOutputs;
            this.totalAppointToday = any.totalAppointToday;
          }else{
          }
        })
      }
    }
  }

  showAppointment: boolean = false;
  showAppointmentDetailToday(data:AppointmentDoctorOutputs){
    this.appointmentDetailToday = data;
    this.showAppointment = true;
  }
  closeShowAppointmentToday(){
    this.showAppointment = !this.showAppointment;
  }
  pageDto!: QuestionPage;
  limit: number = 10;
  page: number = 1;
  questionOutput!: QuestionOutput [];
  totalQuestion: number = 0;
  onLoadingQuestion(){
    this.questionService.getAllQuestion(this.limit,this.page).subscribe((any:QuestionPage) =>{
      if(any.result){
        this.questionOutput = any.simpleResponese.results;
        this.totalQuestion = any.simpleResponese.totalItem;
      }else{
        console.log("false")
      }
    })
  }

  titleQuestion: string = "";
  searchQuestion(){
    this.questionService.getAllQuestion(this.limit,this.page,undefined,undefined,undefined,this.titleQuestion)
    .subscribe((any: QuestionPage) =>{
      if(any.result){
        this.questionOutput = any.simpleResponese.results;
      }
    })
  }

  shouldShowApp() {
    return !this.router.url.endsWith('/medical-report') && !this.router.url.endsWith('/appoinment') && !this.router.url.endsWith('/profile-doctor') && !this.router.url.endsWith('/question') && !this.router.url.endsWith('/invoice');
  }

  isActive(route: string): boolean {
    return this.router.url.includes(route);
  }
  logout(){
    return this.authService.logout();
  }
}
