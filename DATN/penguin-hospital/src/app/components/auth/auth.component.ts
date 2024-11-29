import { BaseDTO, JwtLoginDTO } from './../../model/interface/auth';

import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { LoginAuth, RegisterAuth } from '../../model/class/auth';
import { AuthService } from '../../services/auth/auth.service';
import { FormsModule } from '@angular/forms';
import {  Router } from '@angular/router';




@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent implements OnInit{
  authenService = inject(AuthService);
  ngOnInit(): void {
  }



  constructor(private route: Router){

  }
  authShowLogin = true;
  autheShowSignUp = false;

  loginAuth : LoginAuth = new LoginAuth();
  registerAuth : RegisterAuth = new RegisterAuth();

  showTrade() {
    this.authShowLogin = !this.authShowLogin;
    this.autheShowSignUp = !this.autheShowSignUp;
  }


  onLogin(): void {
    debugger;
    this.authenService.loginAuth(this.loginAuth).subscribe(
      (response: JwtLoginDTO) => {
        if (response.baseDTO.result) {
          localStorage.setItem('user_id', response.user.id.toString())
          localStorage.setItem('token',response.token);
          localStorage.setItem('role', response.role);
          localStorage.setItem('urlImageAvatarUser',response.url)
          localStorage.setItem('avatarUser',response.url)
          localStorage.setItem('fullNameUser', response.user.fullName)
          if(response.role == 'ROLE_DOCTOR'){
            this.route.navigateByUrl('doctor');
          }
          if(response.role == "ROLE_USER"){
            this.route.navigateByUrl('')
          }
        } else {
          alert("Đăng nhập thất bại");
        }
      },
      (error) => {
        console.error('Có lỗi xảy ra:', error);
        alert("Đã có lỗi trong quá trình đăng nhập.");
      }
    );
  }

}
