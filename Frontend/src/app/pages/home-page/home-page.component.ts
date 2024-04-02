import { Component } from '@angular/core';
import { LoggedUser, User } from 'src/app/classes/user';
import { adRent } from 'src/app/interfaces/adRent';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

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
  user: LoggedUser | null = null;

  constructor (private ebService: EcobikeApiService, private userService: UserLoggedService ) {
    
    this.user = this.userService.userLogged;
    if ( this.user == null || this.user != null && this.user.type == 'p') {
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
    }
    
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
