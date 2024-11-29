import { Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { HomeComponent } from './components/home/home.component';
import { authGuard } from './guard/auth.guard';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { ProfileUserComponent } from './components/profile-user/profile-user.component';
import { PetComponent } from './components/profile-user/pet/pet.component';
import { CollectionComponent } from './components/profile-user/collection/collection.component';
import { CommentComponent } from './components/profile-user/comment/comment.component';
import { UserScurityComponent } from './components/profile-user/user-scurity/user-scurity.component';
import { HistoryOrderComponent } from './components/profile-user/history-order/history-order.component';
import { ProfileComponent } from './components/profile-user/profile/profile.component';
import { CreateProfilePetComponent } from './components/profile-user/pet/create-profile-pet/create-profile-pet.component';
import { PetDetailsComponent } from './components/profile-user/pet/pet-details/pet-details.component';

export const routes: Routes = [
  {
    path: 'doctor',
    loadChildren: () => import('./components/admin/doctor/doctor.module').then(m => m.DoctorModule),
    canActivate: [authGuard],
    data: {
      role: 'ROLE_DOCTOR'
    }
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'login',
    component: AuthComponent
  },
  {
    path: 'appointment',
    component: AppointmentComponent
  },
  {
    path: 'profile-user',
    component: ProfileUserComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'pets',
        component: PetComponent,
        children: [
          {
            path: 'pet',
            component: PetDetailsComponent,
          },
          {
            path: 'create-pet',
            component: CreateProfilePetComponent
          }
        ]
      },
      {
        path: 'collection',
        component: CollectionComponent
      },
      {
        path: 'comment',
        component: CommentComponent
      },
      {
        path: 'security',
        component: UserScurityComponent
      },
      {
        path: 'history-order',
        component: HistoryOrderComponent
      },
      {
        path: 'profile',
        component: ProfileComponent
      }
    ]
  }
];
