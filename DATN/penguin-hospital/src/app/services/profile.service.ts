import { BaseDTO, User } from './../model/interface/auth';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Form } from '@angular/forms';
import { BehaviorSubject, catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { UpdateProfileUser } from '../model/class/profile-user';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http : HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  createAvatar(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post(environment.API_PROFILE + 'change-avatar', formData, {
      headers: this.getAuthHeaders()
    });
  }

  updateUser(updateUser : UpdateProfileUser ):Observable<BaseDTO>{
    const token = localStorage.getItem('token');
    if(token){
      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
      return this.http.post<BaseDTO>(`${environment.API_PROFILE}update_profile_user`, updateUser, { headers })
      .pipe(
        catchError(error => {
          console.error('API Error:', error);
          return throwError(() => new Error('Đã xảy ra lỗi khi cập nhật thông tin người dùng.'));
        })
      );
    }else{
      console.error('No token found in local storage')
      return throwError(()=> new Error('Ban chua dang nhap'));
    }
  }

  private avatarSource = new BehaviorSubject<string | null>(null);
  private fullNameSource = new BehaviorSubject<string | null>(null);

  currentAvatar = this.avatarSource.asObservable();
  currentFullName = this.fullNameSource.asObservable();

  updateAvatar(avatar: string) {
    this.avatarSource.next(avatar);
    localStorage.setItem('avatarProfileUser', avatar)
  }

  updateFullName(fullName: string) {
    this.fullNameSource.next(fullName);
    localStorage.setItem('fullNameProfile', fullName)
  }
}
