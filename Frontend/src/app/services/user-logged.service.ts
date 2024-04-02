import { Injectable } from '@angular/core';
import { LoggedUser, User } from '../classes/user';
import { MessageBroadcast } from '../classes/message-broadcast';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserLoggedService {

  private broadcast: MessageBroadcast;
  private listenerVoice = "userObserver";
  private userSubject: Subject<LoggedUser | null>;
  userLogged: LoggedUser | null = null;

  constructor() {
    this.broadcast = MessageBroadcast.Instance;
    this.userSubject = this.broadcast.getOrCreateMessage<LoggedUser | null>(this.listenerVoice);
    if (this.userLogged) {
      this.userSubject.next(this.userLogged);
    }
  }

  login(utente: LoggedUser) {
    this.userLogged = utente;
    this.userSubject.next(utente);
  }

  logout() {
    this.userLogged = null;
    this.userSubject.next(null);
  }

  public bindUpdateUser(onUpdateCallback: (usrData: LoggedUser | null) => void): LoggedUser | null {
    this.broadcast.addListener<LoggedUser | null>(this.listenerVoice, onUpdateCallback);
    return this.userLogged;
  }
}
