import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { Bicicletta } from 'src/app/interfaces/bicicletta';

@Component({
  selector: 'app-ebike',
  templateUrl: './ebike.component.html',
  styleUrls: ['./ebike.component.scss']
})
export class EbikeComponent implements OnInit{

  @Input()
    bicicletta?: Bicicletta

  @Input()
    prezzo?: number = 0;

  firstImage:string = "";
  constructor ( private router: Router) {
  }
  ngOnInit(): void {
    if(this.bicicletta!== undefined && this.bicicletta.img !== undefined){
      const splittedStrings = this.bicicletta.img.split('data:image/jpeg;base64');
      const images : string[] = [];
      splittedStrings.forEach((image: string) => {
        if ( image !== "") {
          images.push('data:image/jpeg;base64'+ image);
        }    
      });
      this.firstImage = images[0];
    }
  }

  clickBike() {
    const navigationExtras: NavigationExtras = {
      queryParams:{
        idBike: this.bicicletta?.id,
        price : this.prezzo
      }
    };
    this.router.navigate(['/dettagli_ebike'], navigationExtras);
  }
}
