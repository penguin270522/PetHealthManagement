import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseDTOSerivce } from '../model/interface/baseDTO';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TypeQuestionService {

  constructor(private http : HttpClient) { }
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllTypeQuestion():Observable<BaseDTOSerivce>{
    return this.http.get<BaseDTOSerivce>(environment.API_URL_TYPE_QUESTION + 'getAllTypeQuestion')
  }
}
