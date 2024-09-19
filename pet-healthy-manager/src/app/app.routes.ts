import { Routes } from '@angular/router';
import { ShopeeComponent } from './components/user/shopee/shopee.component';


export const routes: Routes = [
  {
    path:'doctor',
    loadChildren:() => import('./components/admin/doctor/doctor.module').then(m => m.DoctorModule)
  },
  {
    path:'',
    redirectTo:'home',
    pathMatch:'full'
  },
  {
    path:'home',
    component:ShopeeComponent
  },

];
