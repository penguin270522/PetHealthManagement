import { LoginAuth, User, UserResponse } from './../../model/interface/auth';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseDTO, JwtLoginDTO,  RegisterAuth } from '../../model/interface/auth';
import { BehaviorSubject, catchError, map, Observable, of, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userDataSubject = new BehaviorSubject<User | null>(null);
  isLoginInSubject = new BehaviorSubject<boolean>(false);

  constructor(private httpClient: HttpClient, private router : Router) { }

  createUser(register: RegisterAuth): Observable<BaseDTO> {
    return this.httpClient.post<BaseDTO>(environment.API_AUTH + "register", register);
  }

  loginAuth(login: LoginAuth): Observable<JwtLoginDTO> {
    return this.httpClient.post<JwtLoginDTO>(environment.API_AUTH + "login", login).pipe(
        tap((response: JwtLoginDTO) => {
            if (response.baseDTO.result) {
                this.userDataSubject.next(response.user);
                localStorage.setItem('token', response.token);
                this.isLoginInSubject.next(true);
                this.userDataSubject.subscribe(userData => {
                    if (userData) {
                        console.log("Đăng nhập thành công, dữ liệu người dùng:", userData);
                    }
                });
            } else {
                console.log('Đăng nhập thất bại');
                throw new Error('Đăng nhập thất bại');
            }
        }),
        catchError((error) => {
            console.log('Lỗi khi gọi API đăng nhập:', error);
            return throwError(() => new Error('Lỗi đăng nhập'));
        })
    );
}


  isLoggedIn(): Observable<boolean> {
    return this.isLoginInSubject.asObservable();
  }


  logout(){
    localStorage.clear();
    this.isLoginInSubject.next(false);
    this.userDataSubject.next(null);
    this.router.navigateByUrl('home');
  }

  getUserData(): Observable<User | null> {
    debugger;
    const token = localStorage.getItem('token');
    if (token) {
        return this.httpClient.get<UserResponse>(environment.API_PROFILE + 'profile_user', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).pipe(
            map(response => response.user),
            tap(user => this.userDataSubject.next(user)),
            catchError((error) => {
                console.error('Lỗi khi lấy dữ liệu người dùng:', error);
                return of(null);
            })
        );
    } else {
        return of(null);
    }
}



}
