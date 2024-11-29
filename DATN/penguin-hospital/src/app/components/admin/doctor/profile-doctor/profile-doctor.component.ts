import { CUSTOM_ELEMENTS_SCHEMA, inject, OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { ProfileService } from '../../../../services/profile.service';
import { BaseDTO } from '../../../../model/interface/auth';
import { environment } from '../../../../../environments/environment.development';

@Component({
  selector: 'app-profile-doctor',
  standalone: true,
  imports: [],
  templateUrl: './profile-doctor.component.html',
  styleUrl: './profile-doctor.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProfileDoctorComponent implements OnInit{

  urlImageAvatarUser : string = environment.API_URL_IMAGE + localStorage.getItem('urlImageAvatarUser');
  selectedFile: File | null = null;
  profileService = inject(ProfileService)
  imgUrl: string = '';
  ngOnInit(): void {

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
          this.urlImageAvatarUser;
          alert(this.imgUrl)
        }else{
          alert(any.message)
        }
      })
    }
  }
}
