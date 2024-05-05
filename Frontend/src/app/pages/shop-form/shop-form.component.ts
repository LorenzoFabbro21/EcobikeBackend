import { formatCurrency } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
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
  mostraSpinner: boolean= false;

  
  showError : boolean = false;
  errorStatus: string = "";
  errorMessage: string = "";

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
  this.mostraSpinner = true;
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

            setTimeout(() => {}, 2000);

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
                this.mostraSpinner = false;
                this.router.navigate(['/personal_area']);
              },error: (error: HttpErrorResponse) => {
                if (error.status === 404) {
                  this.errorStatus = "Error:" + error.status.toString();
                  const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
                  if( errorMessageParts.length == 1) {
                    this.errorMessage = errorMessageParts[0];
                  }
                  else {
                    const errorMessage = errorMessageParts.slice(1).join(':').trim();
                    this.errorMessage = errorMessage;
                  }
                  
                  this.showError = true;
                  this.mostraSpinner = false;
                } else if (error.status === 400) {
                  this.errorStatus = "Error:" + error.status.toString();
                  const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
                  if( errorMessageParts.length == 1) {
                    this.errorMessage = errorMessageParts[0];
                  }
                  else {
                    const errorMessage = errorMessageParts.slice(1).join(':').trim();
                    this.errorMessage = errorMessage;
                  }
                  this.mostraSpinner = false;
                  this.showError = true;
                } else {
                  this.errorStatus = "Error:" + error.status.toString();
                  const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
                  if( errorMessageParts.length == 1) {
                    this.errorMessage = errorMessageParts[0];
                  }
                  else {
                    const errorMessage = errorMessageParts.slice(1).join(':').trim();
                    this.errorMessage = errorMessage;
                  }
                  this.mostraSpinner = false;
                  this.showError = true;
                }
              }
            });
          }
        }
      });
    }
  }
  changeValue (event : boolean | any) :void {
    this.showError = event;
  }
}

