import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-bikes-forsale',
  templateUrl: './bikes-forsale.component.html',
  styleUrls: ['./bikes-forsale.component.scss']
})
export class BikesForsaleComponent{

  bikesVendita: Bicicletta[] = [];
  mostraSpinner:boolean = false;
  bikesNull = false;
  bikeSellPrice: bikeRentSell[] = [];

  
  constructor (private router: Router, private ebService: EcobikeApiService, private userService: UserLoggedService) {

    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      this.mostraSpinner = true;
      this.ebService.list_bikes_forsale_by_user(this.userService.userLogged?.id).subscribe({
        next:(response: Bicicletta[]) => {
          if(response) {
            
            this.ebService.elenco_vendite_not_sold().subscribe({
              next:(result: adSell[]) => {
                if(result.length != 0 && result != null && result) {
                  response.forEach(bike => {
                    result.forEach(sell => {
                      if(bike.id == sell.idBike) {
                        const obj: bikeRentSell= {
                          bike: bike,
                          price: sell.price ? sell.price : 0
                        };
                        this.bikeSellPrice.push(obj);   
                      }
                      this.mostraSpinner = false;
                    });
                  });
                }
                else {
                  this.bikesNull= true;
                  this.mostraSpinner= false;
                }
              }
            });
          }
          else {
            this.bikesNull= true;
            this.mostraSpinner= false;
          }
        }
      });
    }
    else {
      this.router.navigate(['/']);
    }
  }
}