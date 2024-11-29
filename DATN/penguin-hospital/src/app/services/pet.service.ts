import { HttpClient, HttpHandler, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pet, PetDetails, PetWithStatus } from '../model/class/pet';
import { Observable, throwError } from 'rxjs';
import { PetProfile } from '../model/interface/basePet';
import { environment } from '../../environments/environment.development';
import { BaseDTO } from '../model/interface/auth';
import { APIResponseModel } from '../model/interface/appointment';

@Injectable({
  providedIn: 'root'
})
export class PetService {

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  createProfilePet(petProfile : Pet ):Observable<PetProfile>{
    const token = localStorage.getItem('token')
    if(token){
      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
      return this.http.post<PetProfile>(environment.API_URL_PET + 'createPet', petProfile, {headers})
    }else{
      console.error('No token found in local storage')
      return throwError(()=> new Error('Ban chua dang nhap'));
    }
  }
  createImagePet(petId:number, file: File):Observable<BaseDTO>{
    const formData: FormData = new FormData();
    formData.append('files', file, file.name);
    return this.http.post<BaseDTO>(environment.API_URL_IMAGE_CONTROLLER + 'pet/upload/' + petId, formData, {
      headers: this.getAuthHeaders()
    });
  }

  getAllPet(userId:string , limit: number, page: number):Observable<APIResponseModel>{
    const params = new HttpParams().set('userId',userId.toString())
    .set('page', page.toString())
    .set('limit',limit.toString());
    return this.http.get<APIResponseModel>(environment.API_URL_PET + 'getAll',{
      headers: this.getAuthHeaders(),
      params
    })
  }

  getPetWithId(petId: number):Observable<PetDetails>{
    return this.http.get<PetDetails>(environment.API_URL_PET + petId,{
      headers: this.getAuthHeaders(),
    })
  }

  getPetWithStatus(userId:string , limit: number, page: number):Observable<PetWithStatus>{
    const params = new HttpParams().set('userId',userId.toString())
    .set('page', page.toString())
    .set('limit',limit.toString());
    return this.http.get<PetWithStatus>(environment.API_URL_PET + 'getPetWithStatusActive',{
      headers: this.getAuthHeaders(),
      params
    })
  }
  getPetWithMedical(userId:string , limit: number, page: number):Observable<PetWithStatus>{
    const params = new HttpParams().set('userId',userId.toString())
    .set('page', page.toString())
    .set('limit',limit.toString());
    return this.http.get<PetWithStatus>(environment.API_URL_PET + 'getPetHaveMedicalReport',{
      headers: this.getAuthHeaders(),
      params
    })
  }

  deletePetWithId(petId:number):Observable<BaseDTO>{
    return this.http.delete<BaseDTO>(environment.API_URL_IMAGE_CONTROLLER + 'pet/delete/' + petId,{
      headers: this.getAuthHeaders()
    })
  }

  uploadPet(petId:number, profilePetUpdate: Pet):Observable<BaseDTO>{
    return this.http.post<BaseDTO>(environment.API_URL_PET + "updatePet/" + petId, profilePetUpdate,{
      headers:this.getAuthHeaders()
    })
  }

}
