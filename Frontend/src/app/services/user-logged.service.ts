import { Injectable } from '@angular/core';
import { LoggedUser, User } from '../classes/user';

@Injectable({
  providedIn: 'root'
})
export class UserLoggedService {


  userLogged?: LoggedUser;

  constructor() { }

  login(utente: LoggedUser) {
    this.userLogged = utente;
  }

  logout() {
    this.userLogged = undefined;
  }
}
