import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { IonicModule } from '@ionic/angular';


@Component({
  selector: 'app-homepage-doctor',
  standalone: true,
  imports: [RouterOutlet,RouterLink,IonicModule],
  templateUrl: './homepage-doctor.component.html',
  styleUrl: './homepage-doctor.component.scss'
})
export class HomepageDoctorComponent {

}
