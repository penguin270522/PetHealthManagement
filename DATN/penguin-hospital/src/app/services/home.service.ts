import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseDTO, UserReponse } from '../model/interface/auth';
import { environment } from '../../environments/environment.development';
import { AppointmentAccount, AppointmentNoAccount } from '../model/class/appointmentss';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient) {
   }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }
  showDoctor(role: String):Observable<UserReponse>{
    const params = new HttpParams().set('role',role.toString());
    return this.http.get<UserReponse>(environment.API_URL_HOME, {
      params
    });
  }

  creatAppointmentForUserNoAccount(appointmentNoAccount : AppointmentNoAccount):Observable<BaseDTO>{
    return this.http.post<BaseDTO>(environment.API_DOCTOR + "createAppointment", appointmentNoAccount)
  }

  creatAppointmentForUserAccount(appointmentAccount : AppointmentAccount):Observable<BaseDTO>{
    return this.http.post<BaseDTO>(environment.API_DOCTOR + "createHaveAccountAppointment", appointmentAccount,{
      headers: this.getAuthHeaders()
    })
  }
}
