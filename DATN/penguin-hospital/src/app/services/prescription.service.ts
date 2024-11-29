import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InvoiceBase, InvoiceDTO, InvoiceInput, MedicineDTO, Prescription, PrescriptionDTO, TypeMedicineDTO, UserPetWithMedicalReportOutPut } from '../model/interface/prescription';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {

  constructor(private http : HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
      const token = localStorage.getItem('token');
      return new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
    }

  getTypeMidicine():Observable<TypeMedicineDTO>{
    return this.http.get<TypeMedicineDTO>(environment.API_URL_TYPE_MEDICINE + 'getAllTypeMedicine',{
      headers: this.getAuthHeaders()
    })
  }
  getMedicineWithTypeMedicine(typeMedicineId: number):Observable<MedicineDTO>{
    return this.http.get<MedicineDTO>(environment.API_URL_MEDICINE + 'typeMedicine/' + typeMedicineId,{
      headers: this.getAuthHeaders()
    })
  }

  createPrescription(numberMedicalReportId: number, prescription: Prescription):Observable<PrescriptionDTO>{
    return this.http.post<PrescriptionDTO>(environment.API_URL_PRESCRIPTION + 'createPrescription/' + numberMedicalReportId, prescription, {
      headers: this.getAuthHeaders()
    })
  }

  getPrescriptionDetails(numberMedicalReportId: number):Observable<PrescriptionDTO>{
    return this.http.get<PrescriptionDTO>(environment.API_URL_PRESCRIPTION + 'prescriptionDetails/' + numberMedicalReportId,{
      headers: this.getAuthHeaders()
    })
  }

  getUserPetWithMedicalReportOutPut(numberMedicalReportId: number):Observable<UserPetWithMedicalReportOutPut>{
    return this.http.get<UserPetWithMedicalReportOutPut>(environment.API_URL_INVOICE + 'getUserAndPetWithMedicalReport/' + numberMedicalReportId,{
      headers: this.getAuthHeaders()
    })
  }

  createInvoice(invoiceInput : InvoiceInput , medicalReportId:number):Observable<InvoiceDTO>{
    return this.http.post<InvoiceDTO>(environment.API_URL_INVOICE + 'createInvoice/' + medicalReportId, invoiceInput,{
      headers: this.getAuthHeaders()
    })
  }

  getAllInvoiceWithDoctorId(doctorId: string):Observable<InvoiceBase>{
    return this.http.get<InvoiceBase>(environment.API_URL_INVOICE + 'getAllInvoiceWithId/' + doctorId,{
      headers: this.getAuthHeaders()
    })
  }
}
