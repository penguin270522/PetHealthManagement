import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentInput, CommentOutPut } from '../model/interface/comment';
import { environment } from '../../environments/environment.development';
import { BaseDTO } from '../model/interface/auth';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllCommentQuestionWithId(id:number):Observable<CommentOutPut>{
    return this.http.get<CommentOutPut>(environment.API_URL_COMMENT + 'getAllCommentQuestion/' + id)
  }

  createComment(commentInput: CommentInput):Observable<BaseDTO>{
    return this.http.post<BaseDTO>(environment.API_URL_COMMENT + 'createComment',commentInput,{
      headers:this.getAuthHeaders()
    })
  }
}
