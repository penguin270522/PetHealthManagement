import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseDTO } from '../model/interface/auth';
import { environment } from '../../environments/environment.development';
import { BaseDTOSerivce } from '../model/interface/baseDTO';

@Injectable({
  providedIn: 'root'
})
export class ServiceMedicalService {

  constructor(private http : HttpClient) { }
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllServiceMedical():Observable<BaseDTOSerivce>{
    return this.http.get<BaseDTOSerivce>(environment.API_URL_SERVICE_MEDICAL);
  }
}
