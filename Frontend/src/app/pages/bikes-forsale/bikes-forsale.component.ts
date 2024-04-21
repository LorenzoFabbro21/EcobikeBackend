import { Component, OnInit } from '@angular/core';
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
  mostraSpinner:boolean = true;
  bikesNull = false;
  bikeSellPrice: bikeRentSell[] = [];

  
  constructor (private ebService: EcobikeApiService, private userService: UserLoggedService) {

    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      this.ebService.list_bikes_forsale_by_user(this.userService.userLogged?.id).subscribe({
        next:(response: Bicicletta[]) => {
          if(response.length != 0) {
            this.mostraSpinner = false;
            this.ebService.elenco_vendite_not_sold().subscribe({
              next:(result: adSell[]) => {
                response.forEach(bike => {
                  result.forEach(sell => {
                    if(bike.id == sell.idBike) {
                      const obj: bikeRentSell= {
                        bike: bike,
                        price: sell.price ? sell.price : 0
                      };
                      this.bikeSellPrice.push(obj);
                    }
                  });
                });
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
  }
}