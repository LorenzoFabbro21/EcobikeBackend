import { Component } from '@angular/core';
import { adRent } from 'src/app/interfaces/adRent';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent {
  bikesNoleggio: Bicicletta[] = [];
  bikesVendita: Bicicletta[] = [];
  rents: adRent[] = [];
  sells: adSell[]= [];
  bikeRentPrice:bikeRentSell[]= [];
  bikeSellPrice:bikeRentSell[]= [];

  constructor (private ebService: EcobikeApiService) {


    /* this.ebService.findFilteredBikes("Olmo",undefined, undefined).subscribe({
      next: (response:Bicicletta[]) => {

        if (response != null) {
          console.log(response);
        }
      }
    }); */

    this.ebService.elenco_noleggi().subscribe({
      next: (response:adRent[]) => {

        if (response != null) {
          this.rents = response;
          this.ebService.elenco_bici_noleggio().subscribe({
            next: (response:Bicicletta[]) => {
      
              if (response != null) {
                this.bikesNoleggio= response;
                this.rents.forEach(rent => {
                  this.bikesNoleggio.forEach(bike => {
                    if(rent.idBike == bike.id) {
                      const obj: bikeRentSell= {
                        bike: bike,
                        price: rent.price ? rent.price : 0
                      };
                      this.bikeRentPrice.push(obj);
                    }
                  });
                });
              }
            }
          });
        }
      }
    });
    
    this.ebService.elenco_vendite().subscribe({
      next: (response:adSell[]) => {

        if (response != null) {
          this.sells = response
          this.ebService.elenco_bici_vendita().subscribe({
            next: (response:Bicicletta[]) => {
      
              if (response != null) {
                this.bikesVendita= response;
      
                this.sells.forEach(rent => {
                  this.bikesVendita.forEach(bike => {
                    if(rent.idBike == bike.id) {
                      const obj: bikeRentSell= {
                        bike: bike,
                        price: rent.price ? rent.price : 0
                      };
                      this.bikeSellPrice.push(obj);
                    }
                  });
                });
              }
            }
          });
        }
      }
    });
    
  }
}
