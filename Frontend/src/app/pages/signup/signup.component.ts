import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { signupRequest } from 'src/app/classes/signupRequest';
import { LoggedUser } from 'src/app/classes/user';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  last_name: string="";
  first_name: string="";
  mail: string="";
  phone_number: string="";
  password: string = "";
  showError : boolean = false;
  errorStatus: string = "";
  errorMessage: string = "";
  constructor(private ebService: EcobikeApiService, private userLogin: UserLoggedService,  private router: Router) {

  }
  signup() {
    const signup : signupRequest =  {
      name: this.first_name,
      lastName: this.last_name,
      mail: this.mail,
      phoneNumber: this.phone_number,
      password: this.password
    }

    this.ebService.signup(signup).subscribe({
      next: (response:any) => { 
        console.log(response);
        this.router.navigate(['../login-form']);

      },
      error: (error: HttpErrorResponse) => {
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
          
          this.showError = true;
        }
      }
    })
    
  }

  changeValue (event : boolean | any) :void {
    this.showError = event;
  }

}
