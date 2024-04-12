import { Component, OnInit, ViewChild, ElementRef  } from '@angular/core';
import { loginRequest } from 'src/app/classes/loginRequest';
import { LoggedUser, User } from 'src/app/classes/user';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';
import { jwtDecode } from "jwt-decode";
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
    
@Component({
  selector: 'app-root',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

     
  url?:String;
  mail: String = "";
  password: String = "";
  showError : boolean = false;
  errorStatus: string = "";
  errorMessage: string = "";
  constructor (private ebService: EcobikeApiService, private userLogin: UserLoggedService,  private router: Router) {

  }
  
  ngOnInit(): void {
 }
    
  googleLogin() {
    window.location.href = 'http://localhost:8090/oauth2/authorization/google';
  }

  signupHref() {
    window.location.href = 'http://localhost:32000/signup';
  }

  login() {
    const login : loginRequest =  {
      email: this.mail,
      password: this.password
    }
    this.ebService.login(login).subscribe({
      next: (response:any) => { 
        const token = response.token; // Supponendo che il token sia contenuto all'interno dell'oggetto di risposta con la chiave 'token'
        const decoded : any = jwtDecode(token);
        this.ebService.getPrivateUser(decoded.sub).subscribe({
          next: (response:User) => { 
            if (response == null) {
              this.ebService.getDealer(decoded.sub).subscribe({
                next: (response:User) => { 
                  const userLogged: LoggedUser = {
                    id: response.id,
                    name: decoded.name,
                    lastName: decoded.last_name,
                    token: token,
                    mail : decoded.sub,
                    exp:decoded.exp,
                    phoneNumber: response.phoneNumber,
                    type:"d"
                  
                    //picture volendo
                  }
                  this.userLogin.login(userLogged);
                  this.router.navigate(['/']);
                }
              });
            }
            else {
              const userLogged: LoggedUser = {
                id: response.id,
                name: decoded.name,
                lastName: decoded.last_name,
                token: token,
                mail : decoded.sub,
                exp:decoded.exp,
                type:"p"
              
                //picture volendo
              }
              this.userLogin.login(userLogged);
              this.router.navigate(['/']);
            }
            
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
              this.errorStatus = error.status.toString();
              this.errorMessage = error.message;
              this.showError = true;
            } else {
              this.errorStatus = error.status.toString();
              this.errorMessage = error.message;
              this.showError = true;
              // Gestire altri tipi di errori qui
            }
          }
        });
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
          this.errorStatus = error.status.toString();
          this.errorMessage = error.message;
          this.showError = true;
        } else {
          this.errorStatus = error.status.toString();
          this.errorMessage = error.message;
          this.showError = true;
          // Gestire altri tipi di errori qui
        }
      }
    });
  }

  changeValue (event : boolean | any) :void {
    this.showError = event;
  }
}