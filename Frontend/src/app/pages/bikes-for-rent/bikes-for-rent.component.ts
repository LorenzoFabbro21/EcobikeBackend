import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { adRent } from 'src/app/interfaces/adRent';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-bikes-for-rent',
  templateUrl: './bikes-for-rent.component.html',
  styleUrls: ['./bikes-for-rent.component.scss']
})
export class BikesForRentComponent {
  bikesVendita: Bicicletta[] = [];
  mostraSpinner:boolean = false;
  bikesNull = false;
  bikeRentPrice: bikeRentSell[] = [];

  
  constructor (private router: Router, private ebService: EcobikeApiService, private userService: UserLoggedService) {

    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      this.mostraSpinner = true;
      this.ebService.list_bikes_forRent_by_user(this.userService.userLogged?.id).subscribe({
        next:(response: Bicicletta[]) => {
          if(response) {
            this.mostraSpinner = false;
            this.bikesVendita = response;
            this.ebService.elenco_noleggi().subscribe({
              next:(result: adRent[]) => {
                if(result.length != 0 && result != null && result) {
                  response.forEach(bike => {
                    result.forEach(sell => {
                      if(bike.id == sell.idBike) {
                        const obj: bikeRentSell= {
                          bike: bike,
                          price: sell.price ? sell.price : 0
                        };
                        this.bikeRentPrice.push(obj);   
                      }
                      this.mostraSpinner = false;
                    });
                  });
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
