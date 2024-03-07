import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adRent } from 'src/app/interfaces/adRent';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

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

  typeAd: string = "";
  firstImage:string = "";
  constructor ( private router: Router, private ebService: EcobikeApiService) {

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

    this.ebService.elenco_vendite().subscribe({
      next: (response:adSell[]) => {
        if (response != null) {
          response.forEach(ad => {
            if(ad.idBike == this.bicicletta?.id) {
              this.typeAd="S";
            }
          });
        }
      }
    });

    this.ebService.elenco_noleggi().subscribe({
      next: (response:adRent[]) => {
        if (response != null) {
          response.forEach(ad => {
            if(ad.idBike == this.bicicletta?.id) {
              this.typeAd="R";
            }
          });
        }
      }
    });
    
  }

  clickBike() {
    const navigationExtras: NavigationExtras = {
      queryParams:{
        idBike: this.bicicletta?.id
      }
    };
    if ( this.typeAd == "R") {
      this.router.navigate(['/dettagli_noleggio'], navigationExtras);
    }
    else {
      this.router.navigate(['/dettagli_vendita'], navigationExtras);
    }
  }
}
