import { InvoiceDTO, InvoiceInput, PrescriptionDTO, UserPetWithMedicalReportOutPut } from './../../../../model/interface/prescription';
import { MedicalReportList } from './../../../../model/interface/medicalReport';
import { MedicalReport } from './../../../../model/class/medicalReport';
import { CommonModule, DatePipe } from '@angular/common';
import { Component, CUSTOM_ELEMENTS_SCHEMA, inject, OnInit} from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DoctorAppointmentService } from '../../../../services/doctor-appointment.service';
import {  MedicalReportModal, MedicalReportModalList } from '../../../../model/interface/medicalReport';
import jsPDF from 'jspdf';
import { PrescriptionService } from '../../../../services/prescription.service';
import { Medicine, MedicineDTO, Prescription, PrescriptionMedicineInput, TypeMedicine, TypeMedicineDTO } from '../../../../model/interface/prescription';
import { ServiceMedicalService } from '../../../../services/service-medical.service';
import { BaseDTOSerivce, ServiceMedical } from '../../../../model/interface/baseDTO';

@Component({
  selector: 'app-medical-report',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './medical-report.component.html',
  styleUrl: './medical-report.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MedicalReportComponent implements OnInit {

  converDtoMedicalReport(data : MedicalReport){
    if(data.genderPet == 'DUC'){
      data.genderPet = 'Đực';
    }
    if(data.genderPet == 'CAI'){
      data.genderPet = 'Cái' ;
    }
  }


  listMedicalReport : MedicalReportList [] = [];
  isDetailVisible: boolean[] = [];
  showMedicalReportTrue = false;
  medicalReport !: MedicalReportList;
  showSuccess = false;
  showErrors = false;
  doctorAppointmentService = inject(DoctorAppointmentService);
  prescriptionService = inject(PrescriptionService);
  serviceMedicalService = inject(ServiceMedicalService)
  page = 1;
  limit = 10;
  showMedicalReport = false;

  onEditMedicalReport(data: MedicalReportList){
    this.medicalReport = data;
    this.showMedicalReportTrue  = !this.showMedicalReportTrue;
  }
  constructor() {

  }

  onShowMedicalReport(data: MedicalReportList){
    debugger;
    this.medicalReport = data;
    console.log(this.medicalReport.urlPet)
    this.showMedicalReport = !this.showMedicalReport;
  }
  ngOnInit(): void {
    this.loadMedicalReport();
    this.getAllServiceMedical();
  }

  toggleDetail(index: number) {
    this.isDetailVisible[index] = !this.isDetailVisible[index];
  }

  editItem(item: any) {
    console.log('Sửa thông tin thuốc:', item);
  }

  deleteItem(item: any) {
    console.log('Xóa thông tin thuốc:', item);
  }

  showCreatMedicalReport(){
    this.showMedicalReportTrue = !this.showMedicalReportTrue;
  }

  createMedicalReport(){
    console.log(this.medicalReport)
    this.doctorAppointmentService.createMedicalReport(this.medicalReport)
    .subscribe((any : MedicalReportModal) => {
      if(any.result){
        this.showMedicalReportTrue = false;
        this.showSuccess = true;
        this.loadMedicalReport();
      }else{
        this.showErrors = true;
        console.log(any.message)
      }
    })
  }

  loadMedicalReport(){
    debugger;
      this.doctorAppointmentService.getAllMedicalReport(this.page , this.limit).subscribe((any: MedicalReportModalList) =>
      {
        this.listMedicalReport = any.results
        this.listMedicalReport.forEach(
          report => {
            this.converDtoMedicalReport(report)
          }
        )
      }
    )
  }



  closeReport(){
    this.showErrors = false;
    this.showSuccess = false;
    this.showMedicalReport = false;
  }

  submitMedicalForm(form: any) {
    if (form.valid) {
      console.log('Form đã được submit:', this.medicalReport);
    } else {
      console.log('Form chưa hợp lệ');
    }
  }



  typeMedicine!: TypeMedicine[];
  getAllTypeMedicine(){
    this.prescriptionService.getTypeMidicine().subscribe((any:TypeMedicineDTO)=>{
      if(any.result){
        this.typeMedicine = any.results;
      }
    })
  }

  medicineList!: Medicine[];
  getMedicineWithTypeMedicine(typeMedicineId: number){
    this.prescriptionService.getMedicineWithTypeMedicine(typeMedicineId).subscribe((any:MedicineDTO)=>{
      if(any.result){
        this.medicineList = any.results;
      }
    })
  }
  treatmentList: Medicine[] = [];
  totalMedicine:number = 0;
  addToTreatment(medicine: Medicine): void {
    debugger;
    const exists = this.treatmentList.some((item) => item.id === medicine.id);
    if (!exists) {
      this.treatmentList.push({ ...medicine, countMedicine: 1 });
    } else {
      alert('Thuốc này đã có trong danh sách điều trị!');
    }
    this.calculateTotalMedicine();
  }
  calculateTotalMedicine(): void {
    this.totalMedicine = this.treatmentList.reduce((total, medicine) => {
      return total + (medicine.price * medicine.countMedicine);
    }, 0);
  }
  removeFromTreatment(medicine: Medicine): void {
    this.treatmentList = this.treatmentList.filter((item) => item.id !== medicine.id);
    this.calculateTotalMedicine();
  }

  showPrescription: boolean = false;
  tradeShowPrescription(){
    this.showPrescription = !this.showPrescription;
    this.treatmentList = [];
    this.prescription.note = '';
    this.totalMedicine = 0;
  }
  prescription: Prescription = new Prescription();
  convertMedicineToPrescriptionInput(medicines: Medicine[]): PrescriptionMedicineInput[] {
    return medicines.map(medicine => ({
      medicineId: medicine.id,
      countMedicine: medicine.countMedicine,
      instructionsForUse: medicine.instructionsForUse
    }));
  }
  checkCreateOrUpdate:boolean = true;
  showPrescriptionDetails(data : MedicalReportList){
    debugger;

    this.tradeShowPrescription();
    this.getAllTypeMedicine();
    this.prescriptionService.getPrescriptionDetails(data.id).subscribe((any:PrescriptionDTO)=>{
      if(any.result){
        this.prescription = any.prescription;
        this.treatmentList = any.medicineOutPutList
        this.totalMedicine = any.prescription.totalMedicine;
        this.checkCreateOrUpdate = any.checkInvoice
      }else{
        this.prescription.medicalId = data.id;
        this.prescription.namePet = data.namePet;
        this.prescription.oldPet = data.oldPet;
        this.prescription.genderPet = data.genderPet;
        this.prescription.namePetOwen = data.petOwner;
        this.prescription.address = data.address;
        this.prescription.symptom = data.symptom;
        this.prescription.diagnosed = data.diagnosed;
        this.treatmentList = [];
        this.prescription.note = '';
        this.totalMedicine = 0;
        this.checkCreateOrUpdate = !any.checkInvoice;
      }
    })


  }
  createPrescription(){
    debugger;
    this.calculateTotalMedicine();
    this.prescription.medicineInputList = this.convertMedicineToPrescriptionInput(this.treatmentList);
    this.prescription.totalMedicine = this.totalMedicine;
    this.prescriptionService.createPrescription(this.prescription.medicalId, this.prescription).subscribe((any:PrescriptionDTO)=>{
      if(any.result){
        this.showSuccessMessage();
        this.tradeShowPrescription();
      }
    })
  }

  showSuccessPrescription: boolean = false;
  progressWidth: number = 0;
  showError: boolean = false;
  showSuccessMessage() {
    this.showSuccessPrescription = true;
    this.progressWidth = 0;
    const interval = setInterval(() => {
      if (this.progressWidth < 100) {
        this.progressWidth += 2;
      } else {
        clearInterval(interval);
        this.showSuccessPrescription = false;
      }
    }, 100);
  }

  serviceMedicalList !: ServiceMedical[];
  getAllServiceMedical(){
    this.serviceMedicalService .getAllServiceMedical().subscribe((any:BaseDTOSerivce)=>{
      if(any.result){
        this.serviceMedicalList = any.results
      }
    })
  }

  showInvoice : boolean = false;
  amountReceived : number = 0;
  amountToPay : number = 0;
  tradeInvoice(){
    this.showInvoice = !this.showInvoice;
    this.listServiceMedicalInput = [];
    this.totalInvoice = 0;
    this.reducedAmount = 0;
    this.amountReceived = 0;
    this.medicalReportInvoice = 0;
  }
  userPetMedicalReport!: UserPetWithMedicalReportOutPut;
  medicalReportInvoice : number = 0;
  showCreateInvoice(medicalReportId:number){
    debugger;
    this.getAllServiceMedical();
    this.tradeInvoice();
    this.prescriptionService.getUserPetWithMedicalReportOutPut(medicalReportId).subscribe((any : UserPetWithMedicalReportOutPut)=>{
      if(any.result){
        this.userPetMedicalReport = any;
        if(any.serviceMedicalList != null){
          this.addServiceInput(any.serviceMedicalList);
        }
      }
    })
    this.medicalReportInvoice = medicalReportId;
  }

  listServiceMedicalInput: ServiceMedical[] = [];
  addServiceInput(serviceMedical: ServiceMedical): void {
    debugger;
    const exist = this.listServiceMedicalInput.some((item) => item.id === serviceMedical.id);
    if (!exist) {
      this.listServiceMedicalInput.push({ ...serviceMedical });
      this.calculateTotalInvoice();
    } else {
      alert('Dịch vụ này đã có trong danh sách');
    }
  }

  removeFromlistServiceMedicalInput(serviceMedical: ServiceMedical): void {
    this.listServiceMedicalInput = this.listServiceMedicalInput.filter((item) => item.id !== serviceMedical.id);
    this.calculateTotalInvoice();
  }
  totalInvoice:number = 0;
  reducedAmount : number = 0;
  calculateTotalInvoice(): void {
    debugger;
    const subtotal = this.listServiceMedicalInput.reduce((total, serviceMedical) => {
      return total + serviceMedical.feeService;
    }, 0);

    this.totalInvoice = subtotal - this.reducedAmount;
    if (this.totalInvoice < 0) {
      this.totalInvoice = 0;
    }
  }

  calculaAmountReceived(){
    if(this.amountReceived <= this.totalInvoice){
      return this.amountToPay = 0;
    }else{
      return this.amountToPay = this.amountReceived - this.totalInvoice;
    }
  }
  invoiceInput: InvoiceInput = new InvoiceInput();
  noteInvoice: string = '';
  createInvoice(){
    debugger;
    this.invoiceInput.note = this.noteInvoice;
    this.invoiceInput.amountReceived = this.amountReceived;
    this.invoiceInput.discountAmount = this.reducedAmount;
    this.invoiceInput.serviceMedicalId = this.invoiceInput.serviceMedicalId = this.listServiceMedicalInput.map(service => service.id);

    this.prescriptionService.createInvoice(this.invoiceInput,this.medicalReportInvoice).subscribe((any:InvoiceDTO)=>{
      if(any.result){
        this.tradeInvoice();
        this.showSuccessMessageInvocie();
      }
    })
  }

  showSuccessInvoice : boolean = false;
  showSuccessMessageInvocie() {
    this.showSuccessInvoice = true;
    this.progressWidth = 0;
    const interval = setInterval(() => {
      if (this.progressWidth < 100) {
        this.progressWidth += 2;
      } else {
        clearInterval(interval);
        this.showSuccessInvoice = false;
      }
    }, 100);
  }
}
