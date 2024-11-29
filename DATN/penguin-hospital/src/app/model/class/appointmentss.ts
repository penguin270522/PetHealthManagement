export class Appointment {
  id:number = 0;
  code: string = '';
  nameUser: string = '';
  numberPhone: string = '';
  namePet: string = '';
  startDate: string = '';
  appointmentStatus: string = '';
}
export class AppointmentNoAccount{
  doctorId: number = 0;
  numberPhone: string = '';
  namePet: string = '';
  dateTime : string = '';
  message: string = '';
  username: string = '';
  serviceId: number = 0;
}
export class AppointmentAccount{
  doctorId: number = 0;
  petId: number = 0;
  dateTime : String = '';
  message: String = '';
  numberPhone: String = '';
  namePet: String = '';
  username: String | null = null;
  serviceId: number = 0;
}
