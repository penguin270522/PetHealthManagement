
import { Component, inject, OnInit,CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { DoctorAppointmentService } from '../../../../services/doctor-appointment.service';
import { APIResponseModel, APIResponseModelAppointment, AppointmentUser, RepairAppointment } from '../../../../model/interface/appointment';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Appointment } from '../../../../model/class/appointmentss';
import { environment } from '../../../../../environments/environment.development';
import { MedicalReport } from '../../../../model/class/medicalReport';
import { MedicalReportModal } from '../../../../model/interface/medicalReport';


@Component({
  selector: 'app-doctor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './doctor.component.html',
  styleUrl: './doctor.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DoctorComponent implements OnInit {
  appointmentOnj!: AppointmentUser;
  appointmentService = inject(DoctorAppointmentService);
  doctorAppointmentService = inject(DoctorAppointmentService);
  appointmentList!: AppointmentUser [];
  urlImageAvatarUser : string = environment.API_AUTH + localStorage.getItem('urlImageAvatarUser');
  limit:number = 12;
  page:number = 1;
  totalItem = 0;
  totalPage = 0;
  isInputPage = false;
  isOpen = false;
  searchName:string = '';
  ngOnInit(): void {
    this.loadingAppoment();

  }
  toggleCard() {
    this.isOpen = !this.isOpen;
  }
  loadingAppoment() {
    debugger;
    this.appointmentService.getAppointmentAll(this.page, this.limit).subscribe((results: APIResponseModelAppointment) => {
      this.appointmentList = results.simpleResponese.results;
      this.totalItem = results.simpleResponese.totalItem;
      this.totalPage = results.simpleResponese.totalPage;
      this.pageTotal = results.simpleResponese.totalPage;
      this.numberActive = results.simpleResponese.totalActive;
      this.numberPending = results.simpleResponese.totalPending;
      this.numberDisable = results.simpleResponese.totalDisable;
    });
  }

  pageIndex = 1;
  pageTotal = this.totalPage;
  pageFront = this.pageIndex + 1;
  choicePage(): boolean {
    if(this.pageFront < this.pageTotal){
      return true;
    }else{

      return false;
    }
  }
  choicePageIndex(){
    if (this.appointmentStatus.trim() !== ''){
      if(this.pageIndex < this.pageTotal){
        return true
      }else{
        return false;
      }
    }else{
      if(this.pageIndex < this.pageTotal){
        return true
      }else{
        return false;
      }
    }
  }
  showPage(): boolean {
    return this.totalPage > 1;
  }
  showRePage(): boolean{
    return this.page > 1;
  }
  showNextPage(): boolean{
    return this.page < 5;
  }

  nextPage() {
    if (this.appointmentStatus.trim() !== ''){
      if (this.page < this.totalPage) {
        this.page++;
        this.pageIndex = this.page;
        this.pageFront = this.pageIndex + 1;
        this.searchAppointmentStatus();
      }
    }else{
      if (this.page < this.totalPage) {
        this.page++;
        this.pageIndex = this.page;
        this.pageFront = this.pageIndex + 1;
        this.loadingAppoment();
      }
    }
  }

  rePage() {
    if (this.appointmentStatus.trim() !== ''){
      if (this.page > 1) {
        this.page--;
        this.pageIndex = this.page;
        this.pageFront = this.pageIndex + 1; // Cập nhật lại pageFront
        this.searchAppointmentStatus();
      }
    }else{
      if (this.page > 1) {
        this.page--;
        this.pageIndex = this.page;
        this.pageFront = this.pageIndex + 1; // Cập nhật lại pageFront
        this.loadingAppoment();
      }
    }
  }

  choicePageFront() {
    debugger
    if (this.appointmentStatus.trim() !== ''){
      if (this.pageFront <= this.totalPage) {
        this.page = this.pageFront;
        this.pageIndex = this.page;
        this.pageFront = this.pageIndex + 1;
        this.searchAppointmentStatus();
      }else{
        if (this.pageFront <= this.totalPage) {
          this.page = this.pageFront;
          this.pageIndex = this.page;
          this.pageFront = this.pageIndex + 1;
          this.loadingAppoment();
        }
      }
    }
  }

  choicePageTotal() {
    if (this.appointmentStatus.trim() !== ''){
      this.page = this.pageTotal;
      this.pageIndex = this.page;
      this.pageFront = this.pageIndex + 1;
      this.searchAppointmentStatus();
    }else{
      this.page = this.pageTotal;
      this.pageIndex = this.page;
      this.pageFront = this.pageIndex + 1;
      this.loadingAppoment();
    }
  }

  onEdit(data:AppointmentUser){
    this.appointmentOnj = data;
    this.isOpen = !this.isOpen;
  }

  onDelete(id:number){
    this.selectedAppointmentId = id;
    this.isModalVisible = true;

  }

  repairAppointment!: RepairAppointment;

  onSaveClient(){
    this.repairAppointment = new RepairAppointment();
    const id:number = this.appointmentOnj.id;
    debugger;
    if(this.appointmentOnj.statusAppointment != null){
      if(this.appointmentOnj.statusAppointment === "đang chờ"){
         this.repairAppointment.appointmentStatus = "PENDING"
      }
      if(this.appointmentOnj.statusAppointment === "được duyệt"){
        this.repairAppointment.appointmentStatus = "ACTIVE"
      }
      if(this.appointmentOnj.statusAppointment === "đã hủy"){
      this.repairAppointment.appointmentStatus = "DISABLE"
      }
    }
    this.repairAppointment.replayAppointment = this.appointmentOnj.replayAppointment;
    this.appointmentService.onSaveAppintment(id, this.repairAppointment).subscribe((res:APIResponseModel)=>{
      if(res.result){
        this.isOpen = false;
        this.loadingAppoment();
      }else{
        alert(res.message)
      }
    })
  }

  isNotEdit(){
    this.isOpen = false;
  }

  numberActive: number = 0;
  numberPending: number = 0;
  numberDisable: number = 0;


  isModalVisible = false;
  selectedAppointmentId: number | null = null;
  isModalSuccess = false;
  isModalErrors = false;
  confirmDelete() {
    if (this.selectedAppointmentId !== null) {
      this.appointmentService.deleteAppointment(this.selectedAppointmentId!).subscribe((res: APIResponseModel) => {
        if (res.result) {
          this.isModalSuccess = true;
          this.loadingAppoment();
        } else {
          this.isModalErrors = true;
        }
        this.isModalVisible = false;
        this.selectedAppointmentId = null;
      });
    }
  }

  cancelDelete() {
    this.isModalVisible = false;
    this.selectedAppointmentId = null;
  }

  onSuccess(){
    this.isModalSuccess = false
  }

  searchAppointmentName(){
      if (this.searchName.trim()) {
        this.appointmentService.searchAppointmentNameUser(this.page, this.limit, this.searchName).subscribe((results: APIResponseModel) => {
          this.appointmentList = results.simpleResponese.results;
          this.page = results.simpleResponese.page;
          this.totalPage = results.simpleResponese.totalPage;
          this.pageTotal = results.simpleResponese.totalPage;
          this.totalItem = results.simpleResponese.totalItem;
        });
      }
  }

  appointmentStatus:string = '';
  searchAppointmentStatus(){
    this.appointmentService.searchAppoinmentStatus(this.page, this.limit,this.appointmentStatus).subscribe((res:APIResponseModel)=>{
      this.appointmentList = res.simpleResponese.results;
      this.page = res.simpleResponese.page;
      this.totalPage = res.simpleResponese.totalPage;
      this.pageTotal = res.simpleResponese.totalPage;
      this.totalItem = res.simpleResponese.totalItem;
    });
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPage) {
      if(this.appointmentStatus === ''){
        this.page = page;
        this.isInputPage = false;
        this.loadingAppoment();
      }else{
        this.page = page;
        this.isInputPage = false;
        this.searchAppointmentStatus();
      }
    }
  }

  showInputPage(){
    this.isInputPage = true;
  }

  isOpenMedical : boolean = false;
  showCreateMedical(data:AppointmentUser){
    this.isOpenMedical = !this.isOpenMedical;
    this.appointmentCoverMedicalReport = data;
    this.medicalReport.numberPhone = this.appointmentCoverMedicalReport.numberPhone;
    this.medicalReport.namePet = this.appointmentCoverMedicalReport.namePet;
    this.medicalReport.petOwner = this.appointmentCoverMedicalReport.fullNameUser;
    this.medicalReport.appointmentId = this.appointmentCoverMedicalReport.id;
  }
  turnOnMedical(){
    this.isOpenMedical = !this.isOpenMedical;
  }
  appointmentCoverMedicalReport!:AppointmentUser;
  medicalReport : MedicalReport = new MedicalReport();
  createMedicalReport(){
    debugger;
    this.doctorAppointmentService.createMedicalReport(this.medicalReport)
    .subscribe((any : MedicalReportModal) => {
      if(any.result){
        this.isOpenMedical = false;
        this.medicalReport = new MedicalReport();
        this.showSuccessMessage();
      }else{
        this.errorMessage = any.message;
        this.isOpenMedical = false;
        this.medicalReport = new MedicalReport();
        this.showErrorMessage();
      }
    })
  }

  showSuccess: boolean = false;
  progressWidth: number = 0;
  showError: boolean = false;
  showSuccessMessage() {
    this.showSuccess = true;
    this.progressWidth = 0;

    const interval = setInterval(() => {
      if (this.progressWidth < 100) {
        this.progressWidth += 2;
      } else {
        clearInterval(interval);
        this.showSuccess = false;
      }
    }, 100);
  }
  errorMessage:String = '';
  showErrorMessage() {
    this.showError = true;
    this.progressWidth = 0;
    const interval = setInterval(() => {
      if (this.progressWidth < 100) {
        this.progressWidth += 2;
      } else {
        clearInterval(interval);
        this.showError = false;
      }
    }, 100);
  }
}

