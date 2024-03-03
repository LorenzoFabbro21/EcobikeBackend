import { Component, Input } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { Bicicletta } from 'src/app/interfaces/bicicletta';

@Component({
  selector: 'app-ebike',
  templateUrl: './ebike.component.html',
  styleUrls: ['./ebike.component.scss']
})
export class EbikeComponent {

  @Input()
    bicicletta?: Bicicletta

  @Input()
    prezzo?: number = 20

  constructor ( private router: Router) {
  }

  clickBike() {
    const navigationExtras: NavigationExtras = {
      queryParams:{
        ebike: JSON.stringify(this.bicicletta),
        prezzo: this.prezzo
      }
    };
    this.router.navigate(['/dettagli_ebike'], navigationExtras);
  }
}
