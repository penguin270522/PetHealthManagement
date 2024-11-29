import { CommonModule } from '@angular/common';
import { Component, HostListener, inject, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth/auth.service';
import { JwtLoginDTO, User } from './model/interface/auth';
import { environment } from '../environments/environment.development';
import { ProfileService } from './services/profile.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'penguin-hospital';
  isDropdownOpen: boolean = false;
  userData: User | null = null;
  loggin: boolean = false;
  urlImage: string = environment.API_URL_IMAGE;
  avatar: string = localStorage.getItem('avatarUser')?? ''
  authService = inject(AuthService);
  checkLogin = this.authService.isLoggedIn();
  userProfileService = inject(ProfileService)
  constructor(private router: Router, private elementRef: ElementRef) {}
  ngOnInit(): void {
    const checkLogin = localStorage.getItem('isLogin') === 'true';
    this.userProfileService.currentAvatar.subscribe(avatar => {
      if (avatar) {
        this.avatar = avatar;
        localStorage.setItem('avatarProfileUser', avatar);
      }
    });
    this.loggin = checkLogin;
    if(checkLogin){
      this.onLogin();
    }else{
      this.authService.isLoggedIn().subscribe(isLoggedIn => {
        this.loggin = isLoggedIn;
        if (isLoggedIn) {
          localStorage.setItem('isLogin', 'true');
          this.onLogin();
        }
      });
    }
  }

  onLogin() {
    const token = localStorage.getItem('token');
    if (token) {
      this.loggin = true;
      this.authService.getUserData().subscribe(data => {
        if (data) {
          this.userData = data;
          this.avatar = this.urlImage + data.image.url;
          localStorage.setItem('avatarUser', this.avatar);
          localStorage.setItem('fullNameUser', this.userData.fullName);
        }
      });
    } else {
      this.loggin = false;
    }
  }

  shouldShowApp() {
    return !this.router.url.includes('/doctor') && !this.router.url.includes('/profile-user/pets/create-pet');
  }

  toggleDropdown(event: Event) {
    event.stopPropagation();
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  onLogout() {
    this.authService.logout();
    this.loggin = false;
  }


  @ViewChild('dropdownMenu', { static: false }) dropdownMenu!: ElementRef;

  @HostListener('document:click', ['$event.target'])
  onClickOutside(targetElement: HTMLElement) {
    if (this.isDropdownOpen && !this.dropdownMenu?.nativeElement.contains(targetElement)) {
      this.isDropdownOpen = false;
    }
  }
}
