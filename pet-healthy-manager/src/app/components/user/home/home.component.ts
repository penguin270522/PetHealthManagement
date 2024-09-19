import { CommonModule } from '@angular/common';
import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { CarouselImage } from '../../../services/interface/images';
import { SlideComponetsComponent } from '../slide-componets/slide-componets.component';
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule,SlideComponetsComponent,RouterLink,RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  schemas:[CUSTOM_ELEMENTS_SCHEMA]
})
export class HomeComponent implements OnInit {
  slides: CarouselImage[] = [
    {
      url: 'https://vcdn1-dulich.vnecdn.net/2021/07/16/1-1626437591.jpg?w=460&h=0&q=100&dpr=2&fit=crop&s=i2M2IgCcw574LT-bXFY92g',
      title: 'anh-1'
    },
    {
      url: 'https://d1hjkbq40fs2x4.cloudfront.net/2017-08-21/files/landscape-photography_1645.jpg',
      title: 'anh-2'
    },
    {
      url: 'https://d1hjkbq40fs2x4.cloudfront.net/2016-01-31/files/1045-2.jpg',
      title: 'anh-3'
    },
    {
      url: 'https://images2.thanhnien.vn/528068263637045248/2024/2/8/1000008590-1707357005432936065136.jpg',
      title: 'anh-4'
    }
  ];

  iconName: string = 'menu';
  isMenuOpen: boolean = false;

  onToggeMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
    this.iconName = this.isMenuOpen ? 'close' : 'menu';
  }


  // Các route mà bạn muốn ẩn header và footer
  noHeaderFooterRoutes: string[] = ['/login-doctor','/dashboard-doctor','/doctor/schedule']; // ví dụ: /login, /register


  ngOnInit() {

  }
}
