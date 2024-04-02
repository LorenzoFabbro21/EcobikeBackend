import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import {  ActivatedRoute, Router } from '@angular/router';
import { jwtDecode } from "jwt-decode";
import { LoggedUser, User } from 'src/app/classes/user';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss']
})
export class AuthenticationComponent implements OnInit {

  token?:string;

  constructor( private route: ActivatedRoute, private router: Router,private userLogin: UserLoggedService,private ebService: EcobikeApiService) {
    
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
    });

     try {
      if( this.token !== undefined) {
        const decoded : any = jwtDecode(this.token);
        this.ebService.getPrivateUser(decoded.sub).subscribe({
          next: (response:User) => { 
            if (response == null) {
              this.ebService.getDealer(decoded.sub).subscribe({
                next: (response:User) => { 
                  const userLogged: LoggedUser = {
                    id: response.id,
                    name: decoded.name,
                    lastName: decoded.last_name,
                    token: this.token,
                    mail : decoded.sub,
                    exp:decoded.exp,
                    type:"d",
                    image: decoded.picture
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
                token: this.token,
                mail : decoded.sub,
                exp:decoded.exp,
                type:"p",
                image: decoded.picture
              }
              this.userLogin.login(userLogged);
              this.router.navigate(['/']);
            }
            
          }
        });
      }   
    } catch (error) {
      console.error('Error decoding JWT token:', error);
    } 
  }

}
