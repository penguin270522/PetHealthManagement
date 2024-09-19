import { Component } from '@angular/core';

@Component({
  selector: 'app-shopee',
  standalone: true,
  imports: [],
  templateUrl: './shopee.component.html',
  styleUrl: './shopee.component.scss'
})
export class ShopeeComponent {
  cartOpen: boolean = false;
  toggleCart() {
    this.cartOpen = !this.cartOpen;
  }

}
