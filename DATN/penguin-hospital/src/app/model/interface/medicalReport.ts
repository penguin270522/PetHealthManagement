import { MedicalReport } from './../class/medicalReport';
export interface MedicalReportModal{
  message:String,
  result:boolean
}

export interface MedicalReportModalList {
  results: any;
  limit: number;
  page: number;
  totalItem: number;
  totalPage: number;
  totalPending: number;
  totalActive: number;
  totalDisable: number;
}

export interface AppointmentToday{
  appointmentDoctorOutputs : AppointmentDoctorOutputs [];
  totalAppointToday : number;
  message:string,
  results:boolean
}
export interface AppointmentDoctorOutputs{
  url:string,
  namePet:string,
  dateAppointment:string,
  status:string,
  message:string,
  fullNameUser:string,
  date:string,
  time:string,
  serviceMedical:string
}
export interface MedicalReportList {
  id: number
  updatedBy: any
  createdDate: string
  updatedDate: any
  createBy: any
  code: string
  petOwner: string
  namePet: string
  oldPet: string
  weightPet: string
  genderPet: string
  numberPhone: string
  address: string
  symptom: string
  diagnosed: string
  followSchedule: string
  pet: any
  appointmentId: number
  urlPet:string
}
