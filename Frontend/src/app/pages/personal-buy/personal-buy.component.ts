import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { userBikeRentInfo } from 'src/app/interfaces/userBikeRentInfo';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-personal-buy',
  templateUrl: './personal-buy.component.html',
  styleUrls: ['./personal-buy.component.scss']
})

export class PersonalBuyComponent {

  bikesBought: userBikeRentInfo[] = [];
  buy: userBikeRentInfo[] = [];
  mostraSpinner:boolean = false;
  bikesNull = false;

  constructor (private router: Router, private ebService: EcobikeApiService, private userService: UserLoggedService) {

    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      const token: string = this.userService.userLogged?.token;
      this.mostraSpinner = true;
      this.ebService.list_bikes_personal_buy(this.userService.userLogged?.id, token).subscribe({
        next: (response: userBikeRentInfo[]) => {
          if(response) {
            this.buy = response;
            this.buy.forEach(bike => {
              const obj: userBikeRentInfo = {
                user: bike.user,
                bike: bike.bike,
                booking: bike.booking,
                adrent: bike.adrent
              }
              this.bikesBought.push(obj);
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
