import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { userBikeRentInfo } from 'src/app/interfaces/userBikeRentInfo';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-bikes-rented',
  templateUrl: './bikes-rented.component.html',
  styleUrls: ['./bikes-rented.component.scss']
})
export class BikesRentedComponent {

  bikesRented: userBikeRentInfo[] = [];
  rents: userBikeRentInfo[] = [];
  mostraSpinner:boolean = false;
  bikesNull = false;

  constructor (private router: Router, private ebService: EcobikeApiService, private userService: UserLoggedService) {
  
    if ( this.userService.userLogged?.id !== undefined && this.userService.userLogged?.token !== undefined) {
      const token: string = this.userService.userLogged?.token;
      this.mostraSpinner = true;
      this.ebService.list_bikes_rented_by_user(this.userService.userLogged?.id, token).subscribe({
        next: (response: userBikeRentInfo[]) => {
          if(response.length != 0) {
            
            this.rents = response;
            this.rents.forEach(bike => {
              const obj: userBikeRentInfo = {
                user: bike.user,
                bike: bike.bike,
                booking: bike.booking,
                adrent: bike.adrent
              }
              this.bikesRented.push(obj);
              this.mostraSpinner = false;
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
