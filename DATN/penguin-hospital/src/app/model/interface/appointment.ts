
export interface Appointment{
  code: string,
  nameUser:String,
  numberPhone:String,
  namePet:String,
  startDate:string,
  appointmentStatus: string
}

export interface Profile{
  url : String
}

export interface APIResponseModel{
  message:String,
  result:boolean,
  simpleResponese:any,
}

export interface APIResponseModelAppointment{
  message:String,
  result:boolean,
  simpleResponese:SimpleResponese,
}

export interface SimpleResponese{
  results:AppointmentUser [],
  limit: number,
  page: number,
  totalItem: number,
  totalPage: number,
  totalPending: number,
  totalActive: number,
  totalDisable: number,
  message: string,
}

export interface AppointmentUser{
  appointmentStatus: string,
  typeClient: string;
  id:number,
  fullNameUser:string,
  message:string,
  namePet:string,
  urlUser:string,
  urlPet: string,
  codeAppointment: string,
  numberPhone:string,
  startDate:string,
  statusAppointment:string,
  replayAppointment:string,
  serviceMedical:string
}

export class RepairAppointment{
  appointmentStatus: string = "";
  replayAppointment: string = ""
}

export class AppointmentSearchName{
  nameUser:String = ''
}
