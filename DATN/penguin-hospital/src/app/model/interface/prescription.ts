export interface TypeMedicine{
  id: number,
  name:string,
  code:string
}

export interface Medicine{
  id: number
  updatedBy: any
  createdDate: number[]
  updatedDate: any
  createBy: any
  name: string
  information: string
  price: number
  countMedicine: number
  unitMedicine:string
  instructionsForUse:string
}

export interface TypeMedicineDTO{
  message:string,
  result:boolean,
  results: TypeMedicine[]
}

export interface MedicineDTO{
  message:string,
  result:boolean,
  results: Medicine[]
}

export class Prescription{
  medicalId:number = 0;
  note:string = '';
  namePet:string = '';
  oldPet:string  = '';
  genderPet:string = '';
  namePetOwen:string = '';
  address:string = '';
  symptom:string = '';
  diagnosed:string = '';
  medicineInputList: PrescriptionMedicineInput[] = [];
  totalMedicine:number  = 0;

}

export interface PrescriptionMedicineInput{
  medicineId: number,
  countMedicine: number,
  instructionsForUse:string
}

export interface PrescriptionDTO{
  message:string,
  result:boolean,
  prescription:Prescription,
  medicineOutPutList:Medicine[],
  checkInvoice:boolean
}

export interface UserPetWithMedicalReportOutPut{
  message: string
  result: boolean
  userName: string
  profilePet: string
  codePrescription: string
  totalPrescription: number
  serviceMedicalList: ServiceMedicalList
}

export interface ServiceMedicalList{
  id:number
  name: string,
  code: string
  feeService:number
}

export class InvoiceInput{
  prescriptionId:number = 0;
  discountAmount:number = 0;
  note:string = '';
  amountReceived:number = 0;
  paymentMethod:number = 0;
  serviceMedicalId: number [] = []
}
export interface ServiceMedical{
  serviceMedicalId:number;
}

export interface InvoiceDTO{
  message:string,
  result: boolean
}

export interface InvoiceBase{
  message:string,
  result: boolean,
  invoiceOutPut: InvoiceOutPut []
}

export interface InvoiceOutPut{
  code: string
  fullNameClient: string
  namePet:string
  nameDoctor:string
  createDate:string
  totalPrice:number
  totalAmountPaid:number
  totalPriceDontPaid:number
  discountAmount:number
}
