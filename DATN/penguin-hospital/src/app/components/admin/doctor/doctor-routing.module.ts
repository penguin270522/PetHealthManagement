import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DoctorComponent } from './doctor/doctor.component';
import { HomepageComponent } from './homepage/homepage.component';
import { MedicalReportComponent } from './medical-report/medical-report.component';
import { ProfileDoctorComponent } from './profile-doctor/profile-doctor.component';
import { QuestionComponent } from './question/question.component';
import { InvoiceComponent } from './invoice/invoice.component';


const routes: Routes = [
  {
    path: '',
    component: HomepageComponent,
    children: [
      {
        path:'appoinment',
        component:DoctorComponent
      },
      {
        path:'medical-report',
        component:MedicalReportComponent
      },
      {
        path:'profile-doctor',
        component:ProfileDoctorComponent
      },
      {
        path:'question',
        component:QuestionComponent
      },
      {
        path:'invoice',
        component:InvoiceComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule { }
