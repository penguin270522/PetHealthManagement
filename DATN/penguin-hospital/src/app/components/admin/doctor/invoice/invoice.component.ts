import { PrescriptionService } from './../../../../services/prescription.service';
import { InvoiceBase, InvoiceOutPut } from './../../../../model/interface/prescription';
import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-invoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.scss'
})
export class InvoiceComponent implements OnInit {
  prescriptionService = inject(PrescriptionService)
  ngOnInit(): void {
    this.loadInvoice();
  }
  doctorId = localStorage.getItem('user_id');
  listinvoiceOutPut : InvoiceOutPut [] = []
  loadInvoice(){
    if(this.doctorId){
      this.prescriptionService.getAllInvoiceWithDoctorId(this.doctorId).subscribe((any:InvoiceBase)=>{
        if(any.result){
          this.listinvoiceOutPut = any.invoiceOutPut;
        }
      })
    }
  }
}
