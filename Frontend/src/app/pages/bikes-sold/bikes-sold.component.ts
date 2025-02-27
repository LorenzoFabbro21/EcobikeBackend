import { Token } from '@angular/compiler';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/classes/user';
import { adRent } from 'src/app/interfaces/adRent';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { userBikeSellInfo } from 'src/app/interfaces/userBikeSellInfo';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';


@Component({
  selector: 'app-bikes-sold',
  templateUrl: './bikes-sold.component.html',
  styleUrls: ['./bikes-sold.component.scss']
})
export class BikesSoldComponent {

  bikesVendita: Bicicletta[] = [];
  sells: userBikeSellInfo[] = [];
  bikeSold: userBikeSellInfo[] = [];
  mostraSpinner:boolean = false;
  bikesNull = false;


  constructor (private router: Router,private ebService: EcobikeApiService, private userService: UserLoggedService) {
  
    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      const token: string = this.userService.userLogged?.token;
      this.mostraSpinner = true;
      this.ebService.list_bikes_sold_by_user(this.userService.userLogged?.id, token).subscribe({
        next: (response: userBikeSellInfo[]) => {
          if(response.length != 0) {
            this.sells = response;
            this.sells.forEach(bike => {
              const obj: userBikeSellInfo = {
                user: bike.user,
                bike: bike.bike,
                appointment: bike.appointment,
                adsell: bike.adsell
              }
              this.bikeSold.push(obj);
              this.mostraSpinner= false;
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