import { Component } from '@angular/core';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { Shop } from 'src/app/interfaces/shop';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-shops',
  templateUrl: './shops.component.html',
  styleUrls: ['./shops.component.scss']
})
export class ShopsComponent {

  shops: Shop[]=[];
  mostraSpinner: boolean= true;

  constructor (private ebService: EcobikeApiService, private userService: UserLoggedService) {

    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      const token: string = this.userService.userLogged?.token; 
      this.ebService.list_shops(token).subscribe({
        next: (response: Shop[]) => {
          if( response != null) {
            this.shops = response;
          }
          this.mostraSpinner = false;
        }
      });
    }
  }
}
