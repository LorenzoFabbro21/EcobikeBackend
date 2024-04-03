import { Component } from '@angular/core';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { Shop } from 'src/app/interfaces/shop';
import { UserLoggedService } from 'src/app/services/user-logged.service';
import { LoggedUser } from 'src/app/classes/user';

@Component({
  selector: 'app-shops',
  templateUrl: './shops.component.html',
  styleUrls: ['./shops.component.scss']
})
export class ShopsComponent {

  shops: Shop[]=[];
  mostraSpinner: boolean= true;
  user: LoggedUser | null = null; 

  constructor (private ebService: EcobikeApiService, private userService: UserLoggedService) {

    this.user = this.userService.userLogged;

    if ( this.user?.id !== undefined && this.user?.token !== undefined) {
      

      this.ebService.list_shops_for_user(this.user.id, this.user.token).subscribe({
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
