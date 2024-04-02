import { Component, OnInit } from '@angular/core';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
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
  
  constructor (private ebService: EcobikeApiService, private userService: UserLoggedService) {

    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      const token: string = this.userService.userLogged?.token;
      this.ebService.list_bikes_forsale_by_user(this.userService.userLogged?.id, token).subscribe({
        next:(response: Bicicletta[]) => {
          if(response.length != 0) {
            this.mostraSpinner = false;
            this.bikesVendita = response;
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
