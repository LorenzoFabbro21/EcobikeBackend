import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoggedUser, User } from 'src/app/classes/user';
import { Shop } from 'src/app/interfaces/shop';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

interface UploadEvent {
  originalEvent: Event;
  files: File[];
}

@Component({
  selector: 'app-shop-form',
  templateUrl: './shop-form.component.html',
  styleUrls: ['./shop-form.component.scss']
})
export class ShopFormComponent {
  uploadFile: any;
  shop_name?: string;
  city?: string;
  address?: string;
  phone_number?: string;
  img?:string= "";

  constructor(private userService: UserLoggedService, private ebService: EcobikeApiService, private router: Router) {

  }


  onUpload(event:UploadEvent| any) {
    for(let file of event.files) {
      this.uploadFile = file;
  }
  }


  send () {
    
    const reader = new FileReader();
        const file = this.uploadFile;
        reader.onload = (e) => {
          const base64String = (e.target as any).result;
          this.img= this.img + base64String;
          this.createShop(); 
      }
      reader.readAsDataURL(file);

  }


  createShop() {

    if (this.userService.userLogged !== null && this.userService.userLogged?.token !== undefined && this.userService.userLogged.mail !== undefined) {  
      let token : string = this.userService.userLogged?.token;
      const user_private = this.userService.userLogged;
      this.ebService.getPrivateUser(this.userService.userLogged.mail).subscribe({
        next: (user:User) => {
          if ( this.userService.userLogged?.id !== undefined) {
            this.ebService.delete_private(this.userService.userLogged.id!, token).subscribe({
              next: (response: any) => {
                this.ebService.new_dealer(user, token).subscribe({
                  next: (response) => {
                    const idDealer = response.id;
                    const shop: Shop = {
                      name: this.shop_name,
                      address: this.address,
                      city: this.city,
                      phoneNumber: this.phone_number,
                      img: this.img,
                      idUser: response.id
                    };
                    this.ebService.new_shop(shop, token).subscribe({
                      next: (response) => {
                        console.log(response)
                        this.ebService.getDealerById(idDealer,token).subscribe({
                          next: (dealer: User) => {

                            const userLogged: LoggedUser = {
                              id: dealer.id,
                              name: dealer.name,
                              lastName: dealer.lastName,
                              token: token,
                              mail : dealer.mail,
                              exp: user_private.exp,
                              phoneNumber: dealer.phoneNumber,
                              type:"d"
                            }
                            this.userService.logout();
                            this.userService.login(userLogged);
                            this.router.navigate(['/personal_area']);
                          }
                        });

                      }
                    });
                  }
                });
              }
            });
          }
          
        } 
      });
    }
  }
}

