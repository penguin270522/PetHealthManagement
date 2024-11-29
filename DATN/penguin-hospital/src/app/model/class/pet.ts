export class Pet{
  id: number = 0;
  name: String = '';
  genderPet: String = '';
  birthDay: String = '';
  adoptive: String = '';
  weight: number = 0;
  furColor: String = '';
  imagePet:{ id: number; url: string }[] = [];
  crystal: String = ''
}

export interface PetDetails{
  namePet: String;
  oldPet: number;
  genderPet:String;
  colorPet:String;
  birthDay:String;
  adoptive:String;
  crystal:String;
  urlImagePet:String;
  listMedicalReport:[];
  listAppointmentOfPet:ListAppointmentOutput[];
  totalAppointment: number;
  totalMedicalReport: number;
}

export interface ListAppointmentOutput{
  code:String;
  startDate:String;
  namePet:String;
  doctor:String;
  status:String;
  message:String;
}

export interface PetWithStatus{
  message: string
  results: boolean
  petList: Pet[]
  totalAppointmentActive: number
  totalMedicalActive: number
}
