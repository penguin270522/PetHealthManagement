import { BaseDTOQuestion } from './../model/interface/baseDTO';
import { QuestionInput, QuestionPage } from './../model/interface/question';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { BaseDTO } from '../model/interface/auth';
import { BaseDTOSerivce } from '../model/interface/baseDTO';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http : HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllQuestion(limit: number, page: number,
    doctorId?:string,
    userId?:string,
    typeQuestion?:number,
    titleQuestion?:string):Observable<QuestionPage>{
    let params = new HttpParams().set('page', page.toString()).set('limit',limit.toString());
    if(doctorId){
      params = params.set('doctorId', doctorId.toString());
    }
    if(userId){
      params = params.set('UserId', userId.toString())
    }
    if(typeQuestion){
      params = params.set('typeQuestion', typeQuestion.toString())
    }
    if(titleQuestion){
      params = params.set('titleQuestion', titleQuestion.toString())
    }
    return this.http.get<QuestionPage>(environment.API_URL_QUESTION + 'getAllQuestion',{
      params,
      headers: this.getAuthHeaders()
    })
  }

  createQuestionHaveAccount(formQuestion: QuestionInput):Observable<BaseDTOSerivce>{
    return this.http.post<BaseDTOSerivce>(environment.API_URL_QUESTION + 'create', formQuestion, {
      headers: this.getAuthHeaders()
    })
  }

  getQuestionDetail(id:number):Observable<BaseDTOQuestion>{
    return this.http.get<BaseDTOQuestion>(environment.API_URL_QUESTION + 'getQuestionWithId/' + id,{
      headers: this.getAuthHeaders()
    })
  }

  deleteQuestion(id:number):Observable<BaseDTOQuestion>{
    return this.http.delete<BaseDTOQuestion>(environment.API_URL_QUESTION + 'deleteQuestionId/' + id,{
      headers: this.getAuthHeaders()
    })
  }

}
