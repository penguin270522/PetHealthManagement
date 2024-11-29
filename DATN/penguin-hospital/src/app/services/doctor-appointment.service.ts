import { AuthService } from './auth/auth.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { APIResponseModel, APIResponseModelAppointment,  RepairAppointment } from '../model/interface/appointment';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { MedicalReport } from '../model/class/medicalReport';
import { AppointmentToday, MedicalReportModal, MedicalReportModalList } from '../model/interface/medicalReport';

@Injectable({
  providedIn: 'root'
})
export class DoctorAppointmentService {

  constructor(private http: HttpClient, private autheService : AuthService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAppointmentAll(page:number, limit:number):Observable<APIResponseModelAppointment>{
    const params = new HttpParams()
    .set('page', page.toString())
    .set('limit', limit.toString())
    return this.http.get<APIResponseModelAppointment>(environment.API_DOCTOR + "getAppointmentAll",{
      headers: this.getAuthHeaders(),
      params});
  }

  deleteAppointment(id:number):Observable<APIResponseModel>{
    return this.http.delete<APIResponseModel>(environment.API_DOCTOR +"deleteAppoint/"+ id)
  }
  onSaveAppintment(id:number, obj:RepairAppointment):Observable<APIResponseModel>{
    return this.http.put<APIResponseModel>(environment.API_DOCTOR +"editAppointment/"+id , obj,{
      headers: this.getAuthHeaders()
    })
  }

  searchAppointmentNameUser(page:number, limit:number,nameUser:string):Observable<APIResponseModel>{
    const params = new HttpParams().set('page',page.toString()).set('limit',limit.toString()).set('nameUser',nameUser.toString())
    return this.http.get<APIResponseModel>(environment.API_DOCTOR + 'searchAppointmentUserName', {
      params
  });
  }
  searchAppoinmentStatus(page:number,limit:number,status:string):Observable<APIResponseModel>{
    const params = new HttpParams().set('page', page.toString()).set('limit', limit.toString()).set('status',status.toString())
    return this.http.get<APIResponseModel>(environment.API_DOCTOR+'searchAppointmentStatus',{
      headers: this.getAuthHeaders(),
      params
    })
  }

  createMedicalReport(medicalReport : MedicalReport): Observable<MedicalReportModal>{
    return this.http.post<MedicalReportModal>(environment.API_DOCTOR + 'medicalReport', medicalReport,{
      headers: this.getAuthHeaders()
    })
  }

  getAllMedicalReport(page : number , limit: number ):Observable<MedicalReportModalList>{
    const params = new HttpParams().set('page', page.toString()).set('limit', limit.toString())
    return this.http.get<MedicalReportModalList>(environment.API_DOCTOR + 'getAllMedicalReport',{
      headers: this.getAuthHeaders(),
      params
    } )
  }

  getAllAppointmentToday(doctorId: string,dateNow:String):Observable<AppointmentToday>{
    const params = new HttpParams().set('doctorId', doctorId.toString());
    return this.http.get<AppointmentToday>(environment.API_DOCTOR + 'appointments/today/' + dateNow,{
      headers: this.getAuthHeaders(),
      params
    })
  }
}
