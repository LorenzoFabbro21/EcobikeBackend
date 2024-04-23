import { formatCurrency } from '@angular/common';
import { Component } from '@angular/core';
import { withNoDomReuse } from '@angular/platform-browser';
import { PRIMARY_OUTLET, Router } from '@angular/router';
import { LoggedUser, User } from 'src/app/classes/user';
import { Private } from 'src/app/interfaces/private';
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
    /*
    1> get utente
    2> cancella utente
    3> crea un dealer con le informazioni prese al punto 1
    4> crea un negozio con le informazioni del form
    5> get informazioni dealer
    6> login
    */

    /*
    rabbitMQ
    1> get utente
    2> chiamata shop con parametri (utente, token?, shop) che ritorna id dealer
    3> chiamata get dealer by id

    */

    if (this.userService.userLogged !== null && this.userService.userLogged?.token !== undefined && this.userService.userLogged.mail !== undefined) {  
      let token : string = this.userService.userLogged?.token;
      const user_private = this.userService.userLogged;
      this.ebService.getPrivateUser(this.userService.userLogged.mail).subscribe({
        next: (user:User) => {
          if (this.userService.userLogged?.id !== undefined) {
          
            const shop: Shop = {
              name: this.shop_name,
              address: this.address,
              city: this.city,
              phoneNumber: this.phone_number,
              img: this.img,
              idUser: 0 //da modificare nel backend
            }

            console.log(user);
            console.log(shop);
            
            this.ebService.new_shop_rabbit(shop, user, token).subscribe({
              next: (response) => {
                console.log(response)

                const userLogged: LoggedUser = {
                  id: response.id,
                  name: response.name,
                  lastName: response.lastName,
                  token: token,
                  mail : response.mail,
                  exp: user_private.exp,
                  phoneNumber: response.phoneNumber,
                  type:"d"
                }
                console.log("------------------------------------------------------------")
                console.log(userLogged);
                this.userService.logout();
                this.userService.login(userLogged);
                this.router.navigate(['/personal_area']);
              }
            });
          }
        }
      });
    }
  }


/*
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
  */
}

