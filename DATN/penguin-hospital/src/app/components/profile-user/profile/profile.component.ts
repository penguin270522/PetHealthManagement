import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth/auth.service';
import { environment } from '../../../../environments/environment.development';
import { Router } from '@angular/router';
import { BaseDTO, User } from '../../../model/interface/auth';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UpdateProfileUser } from '../../../model/class/profile-user';
import { ProfileService } from '../../../services/profile.service';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule, IonicModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  selectedFile: File | null = null;
  avatar = environment.API_URL_IMAGE + localStorage.getItem('urlImageAvatarUser');
  loggin = false
  createdDate : any;
  authService = inject(AuthService);
  profileService = inject(ProfileService);
  updateProfileForm! : FormGroup;
  user: User | undefined;
  profileUser = new UpdateProfileUser();
  showNotification = false;
  ngOnInit(): void {
    this.loadingUser();
    this.updateProfileForm = this.fb.group({
      fullName:['',Validators.required],
      numberPhone:['',[Validators.required, Validators.pattern('^[0-9]{10}$')]],
      cmnd:['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
      address:['', Validators.required],
      genderUser: ['', Validators.required]
    })
  }
  constructor(private router: Router,private fb: FormBuilder){

  }
  loadingUser() {
    const token = localStorage.getItem('token');
    if (token) {
        this.authService.getUserData().subscribe(user => {
            if (user) {
                debugger;
                this.loggin = true;
                this.user = user;
                this.createdDate = this.mapperDate(this.user.createdDate)
                localStorage.setItem('fullName', user.fullName);
                localStorage.setItem('avatarUser', this.avatar);
                this.updateProfileForm.patchValue({
                  fullName: user.fullName,
                  numberPhone: user.phoneNumber,
                  cmnd: user.cmnd,
                  genderUser: user.genderUser,
                  address: user.address
                })
            } else {
                console.error('Người dùng không tồn tại');
            }
        });
    } else {
        this.loggin = false;
    }
  }

  mapperDate(date: number[]): string {
    if (date.length < 7) {
      throw new Error('Mảng ngày tháng phải có ít nhất 7 phần tử.');
    }
    const createdDate = new Date(Date.UTC(
      date[0],
      date[1] - 1,
      date[2],
      date[3],
      date[4],
      date[5],
      date[6]
    ));
    const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
    return createdDate.toLocaleDateString('vi-VN', options);
  }

  updateProfileUser(){
    if(this.updateProfileForm.valid){
      debugger;
      this.profileUser.fullName = this.updateProfileForm.value.fullName;
      this.profileUser.address = this.updateProfileForm.value.address;
      this.profileUser.cmnd = this.updateProfileForm.value.cmnd;
      this.profileUser.genderUser = this.updateProfileForm.value.genderUser;
      this.profileUser.numberPhone = this.updateProfileForm.value.numberPhone;
      this.profileService.updateUser(this.profileUser).subscribe((any:BaseDTO)=>{
        if(any.result){
          console.log(any.message);
          this.showNotification = true;
          this.profileService.updateFullName(this.profileUser.fullName);
          setTimeout(()=>{
            this.showNotification = false;
          },5000);

        }else{
          console.error(any.message);
        }
      })
    }
  }
  selectFile() {
    const fileInput = document.getElementById('file-input') as HTMLInputElement;
    if (fileInput) {
      fileInput.click();
    }
  }
  onFileChange(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedFile = fileInput.files[0];
      console.log(this.selectedFile);
      this.uploadFile(this.selectedFile);
    }
  }

  uploadFile(file : File){
    debugger;
    if(this.selectedFile){
      this.profileService.createAvatar(this.selectedFile).subscribe((any:BaseDTO)=>{
        if(any.result){
          const updatedAvatar = environment.API_URL_IMAGE + any.url;
          localStorage.setItem('urlImageAvatarUser', any.url);
          this.profileService.updateAvatar(updatedAvatar)
          this.avatar = environment.API_URL_IMAGE + any.url;
          this.showNotification = true;
          setTimeout(()=>{
            this.showNotification = false;
          }, 5000);
        }else{
          alert(any.message)
        }
      })
    }
  }
}
