import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoggedUser, User } from 'src/app/classes/user';
import { Shop } from 'src/app/interfaces/shop';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-personal-area',
  templateUrl: './personal-area.component.html',
  styleUrls: ['./personal-area.component.scss']
})
export class PersonalAreaComponent {

  image?: string;
  user: LoggedUser | null = null;
  shop: Shop| any;
  constructor(private userService: UserLoggedService, private ebService: EcobikeApiService,private router: Router) {
    if ( this.userService.userLogged?.id == undefined && this.userService.userLogged?.token == undefined) {
      this.router.navigate(['/']);
    }
    
    if (this.userService.userLogged?.image != null) {
      this.image =this.userService.userLogged.image;
    }
    else {
      this.image = 'assets/images/user.jpg';
    }
    if (userService.userLogged?.type == 'd' && userService.userLogged?.token != undefined && userService.userLogged?.mail != undefined && userService.userLogged?.id != undefined) {
      const token : string = userService.userLogged?.token;
      this.ebService.getShopFromUser(userService.userLogged?.id, token).subscribe({
        next: (response:Shop) => {
          this.shop = response;
        }
      });
      this.ebService.getDealer(userService.userLogged?.mail).subscribe({
        next: (response:User) => {
          this.user = userService.userLogged;
          this.user!.phoneNumber = response.phoneNumber;
        }
      });
    }
    else if (userService.userLogged?.type == 'p' && userService.userLogged?.mail != undefined ) {
      this.ebService.getPrivateUser(userService.userLogged?.mail).subscribe({
        next: (response:User) => {
          this.user = userService.userLogged;
          this.user!.phoneNumber = response.phoneNumber;
        }
      });
    }
    
  }
  upgradeProfile() {
    this.router.navigate(['/new_shop']);
  }
}
