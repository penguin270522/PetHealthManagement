import { environment } from './../../../environments/environment.development';
import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'app-profile-user',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './profile-user.component.html',
  styleUrl: './profile-user.component.scss'
})
export class ProfileUserComponent implements OnInit {
  avatar: string = localStorage.getItem('avatarUser')?? ''
  fullName: string = localStorage.getItem('fullNameUser') ?? ''
  userProfileService = inject(ProfileService)
  ngOnInit(): void {
    const storedAvatar = localStorage.getItem('avatarProfileUser');
    const storedFullName = localStorage.getItem('fullNameProfile');

    if (storedAvatar) {
      this.avatar = storedAvatar;
    }
    if (storedFullName) {
      this.fullName = storedFullName;
    }
    this.userProfileService.currentAvatar.subscribe(avatar => {
      if (avatar) {
        this.avatar = avatar;
        localStorage.setItem('avatarProfileUser', avatar);
      }
    });

    this.userProfileService.currentFullName.subscribe(fullName => {
      if (fullName) {
        this.fullName = fullName;
        localStorage.setItem('fullNameProfile', fullName);
      }
    });
  }
}
