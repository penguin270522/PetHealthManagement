import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginAdminComponent } from './login-doctor/login-admin.component';
import { ScheduleComponent } from './schedule/schedule.component';
import { HomepageDoctorComponent } from './homepage-doctor/homepage-doctor.component';

const routes: Routes = [
  {
    path: '',
    component: HomepageDoctorComponent,
    children: [
      { path: 'login', component: LoginAdminComponent },
      { path: 'schedule', component: ScheduleComponent },
      { path: '', component: HomepageDoctorComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule { }
