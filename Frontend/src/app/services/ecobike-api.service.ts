import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Bicicletta } from '../interfaces/bicicletta';
import { Observable } from 'rxjs/internal/Observable';
import { catchError, retry, throwError } from 'rxjs';
import { adRent } from '../interfaces/adRent';
import { adSell } from '../interfaces/adSell';
import { Booking } from '../interfaces/booking';
import { loginRequest } from '../classes/loginRequest';
import { signupRequest } from '../classes/signupRequest';
import { userBikeSellInfo } from '../interfaces/userBikeSellInfo';
import { User } from '../classes/user';
import { Shop } from '../interfaces/shop';
import { Appointment } from '../interfaces/appointment';
import { userBikeRentInfo } from '../interfaces/userBikeRentInfo';

@Injectable({
  providedIn: 'root'
})
export class EcobikeApiService {
  
  constructor(protected httpClient: HttpClient) { }

  url = "http://localhost:8080/api"

  /**
   * Restitusice la bicicletta selezionata in base all'id
   *
   * Endpoint Rest: bike/{bikeNo}
   */
  public get_bicicletta(bikeNo: number): Observable<Bicicletta> {
    return  this.httpClient.get<Bicicletta>(`${this.url}/bike/${bikeNo}`);
  }

  /**
   * Restitusice tutte le biciclette
   *
   * Endpoint Rest: bike
   */
  public get_biciclette(): Observable<Bicicletta[]> {
    return  this.httpClient.get<Bicicletta[]>(`${this.url}/bike`);
  }

  /**
   * Restitusice le biciclette della stesa marca
   *
   * Endpoint Rest: bike/brand/{brand}
   */
  public get_similar_bike(brand: string | undefined): Observable<Bicicletta[]> {
    return this.httpClient.get<Bicicletta[]>(`${this.url}/bike/brand/` + brand);
  }

  /**
  * Restituisce l'elenco dei noleggi di un utente
  *
  * Endpoint Rest: booking/user/{id}/bikes
  */
  public list_bikes_rented_by_user(id : number, token: string): Observable<userBikeRentInfo[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<userBikeRentInfo[]>(`${this.url}/booking/user/` + id + "/bikes", {headers}); 
  }

  /**
  * Restituisce l'elenco delle vendite di un utente
  *
  * Endpoint Rest: appointment/user/{id}/bikes
  */
  public list_bikes_sold_by_user(id : number, token: string): Observable<userBikeSellInfo[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<userBikeSellInfo[]>(`${this.url}/appointment/user/` + id + "/bikes", {headers}); 
  }

  /**
  * Restituisce l'elenco delle bike in noleggio di un utente
  *
  * Endpoint Rest: adrent/user/{id}/bikes
  */
  public list_bikes_forRent_by_user(id : number, token: string): Observable<Bicicletta[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<Bicicletta[]>(`${this.url}/adrent/user/` + id + `/bikes`, {headers}); 
  }

  /**
  * Restituisce l'elenco delle bike in vendita di un utente
  *
  * Endpoint Rest: adsell/user/{id}/bikes
  */
  public list_bikes_forsale_by_user(id : number, token: string): Observable<Bicicletta[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<Bicicletta[]>(`${this.url}/adsell/user/` + id + `/bikes`, {headers}); 
  }

  /**
  * Restituisce l'elenco delle biciclette filtrate
  *
  * Endpoint Rest: bike/filter
  */
  findFilteredBikes(brand?: string, color?: string, size?: string): Observable<Bicicletta[]> {
    let params = new HttpParams(); 
    // Aggiungi i parametri solo se non sono nulli
    if (brand)
      params = params.set('brand', brand);
    if (color) 
      params = params.set('color', color);  
    if (size) 
      params = params.set('size', size);
    return this.httpClient.get<Bicicletta[]>(`${this.url}/bike/filter`, { params });
  }






  /**
  * Restituisce l'elenco delle biciclette a noleggio
  *
  * Endpoint Rest: adrent/bikes
  */
  public elenco_bici_noleggio (): Observable<Bicicletta[]> {
    return this.httpClient.get<Bicicletta[]>(`${this.url}/adrent/bikes`);
  }

  /**
  * Restituisce l'elenco dei noleggi
  *
  * Endpoint Rest: adrent
  */
  public elenco_noleggi(): Observable<adRent[]> {
    return this.httpClient.get<adRent[]>(`${this.url}/adrent`);
  }

  /**
  * Restituisce l'elenco dei noleggi quando l'utente è loggato
  *
  * Endpoint Rest: adrent/all/user/{id}
  */
  public elenco_noleggi_logged_user(idUser: number | undefined, token: string | any): Observable<adRent[]> {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.get<adRent[]>(`${this.url}/adrent/all/user/${idUser}`, options);
  }






  /**
  * Restituisce l'elenco delle biciclette in vendita
  *
  * Endpoint Rest: adsell/bikes
  */
  public elenco_bici_vendita (): Observable<Bicicletta[]> {
    return this.httpClient.get<Bicicletta[]>(`${this.url}/adsell/bikes`);
  }

  /**
  * Restituisce l'elenco delle vendite
  *
  * Endpoint Rest: adsell
  */
  public elenco_vendite(): Observable<adSell[]> {
    return this.httpClient.get<adSell[]>(`${this.url}/adsell`);
  }

  /**
  * Restituisce l'elenco delle vendite quando l'utente è loggato
  *
  * Endpoint Rest: adsell/all/user/{id}
  */
  public elenco_vendite_logged_user(idUser: number | undefined, token: string | any): Observable<adSell[]> {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.get<adSell[]>(`${this.url}/adsell/all/user/${idUser}`, options);
  }






  /**
  * Restituisce l'elenco dei bookings
  *
  * Endpoint Rest: booking
  */
  public get_bookings(token: string | undefined): Observable<Booking[]> {
    return  this.httpClient.get<Booking[]>(`${this.url}/booking`);
  }

  /**
  * Restituisce l'elenco degli appointments
  *
  * Endpoint Rest: booking
  */
  public get_appointments(): Observable<Appointment[]> {
    return  this.httpClient.get<Appointment[]>(`${this.url}/appointment`);
  }






  /**
  * Restituisce il negozio in base all'utente
  *
  * Endpoint Rest: shop/user/{id}
  */
  getShopFromUser(id: number, token: string) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<Shop>(`${this.url}/shop/user/` + id, {headers});
  }




  /**
   * Restituisce l'elenco degli shops
  *
  * Endpoint Rest: shop
  */
  public list_shops(token: string): Observable<Shop[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<Shop[]>(`${this.url}/shop`, {headers}); 
  }

  /**
   * Restituisce uno shop in base all'id
  *
  * Endpoint Rest: shop/{id}
  */
  public get_shop(id: number, token: string): Observable<Shop> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<Shop>(`${this.url}/shop/` + id, {headers}); 
  }

  /**
  * Restituisce tutti gli shop tranne quello dell'utente loggato
  *
  * Endpoint Rest: shop/all/user/{id}
  */
  public list_shops_for_user(id: number, token: string): Observable<Shop[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<Shop[]>(`${this.url}/shop/all/user/${id}`, {headers}); 
  }






  /**
  * Inserisce una nuova bicicletta
  *
  * Endpoint Rest: bike
  */
  public new_bike(bike: Bicicletta | any, token: string){
   let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
  }); 
  let options = { headers: headers };
    return this.httpClient.post<any>(`${this.url}/bike`, bike, options);
  }

  /**
  * Inserisce un nuovo appointment
  *
  * Endpoint Rest: appointment
  */
  public new_appointment(appointment: Appointment | any, token: string) {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.post<Appointment>(`${this.url}/appointment`, appointment, options); 
  }

  /**
  * Inserisce un nuovo booking
  *
  * Endpoint Rest: booking
  */
  public new_booking(booking: Booking, token: string){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.post<Booking>(`${this.url}/booking`, booking, options);
  }

  /**
  * Inserisce un nuovo noleggio
  *
  * Endpoint Rest: adrent
  */
  public new_noleggio(adRent: adRent | any, token: string){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.post<adRent>(`${this.url}/adrent`, adRent, options);
  }

  /**
  * Inserisce una nuova vendita
  *
  * Endpoint Rest: adsell
  */
  public new_vendita(adsell: adSell | any, token: string){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.post<adSell>(`${this.url}/adsell`, adsell, options);
  }

  /**
  * Inserisce un nuovo dealer
  *
  * Endpoint Rest: dealer
  */
  public new_dealer(user: User, token: string){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.post<any>(`${this.url}/dealer`, user, options);
  }

  /**
  * Inserisce un nuovo shop
  *
  * Endpoint Rest: shop
  */
  public new_shop(shop: Shop, token: string){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.post<any>(`${this.url}/shop`, shop, options);
  }





  
  /**
  * Restituisce il private in base all'email
  *
  * Endpoint Rest: /private/email/{email}
  */  
  getPrivateUser(email: string) {
    return this.httpClient.get<User>(`${this.url}/private/email/` + email);
  }

  /**
  * Restituisce il dealer in base all'email
  *
  * Endpoint Rest: /dealer/email/{email}
  */  
  getDealer(email: string) {
    return this.httpClient.get<User>(`${this.url}/dealer/email/` + email);
  }

  /**
  * Restituisce il dealer in base all'id
  *
  * Endpoint Rest: dealer/{id}
  */ 
  getDealerById(id: number, token: string) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<User>(`${this.url}/dealer/` + id, {headers});
  }






  /**
  * Elimina un utente privato
  *
  * Endpoint Rest: /private
  */
  public delete_private(id: number, token: string){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }); 
    let options = { headers: headers };
    return this.httpClient.delete<signupRequest>(`${this.url}/private/` + id, options);
  }






  /**
  * Fa il login dell'utente
  *
  * Endpoint Rest: http://localhost:8090/auth/login
  */
  public login(login: loginRequest){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    }); 
    let options = { headers: headers,  };
    return this.httpClient.post<loginRequest>(`http://localhost:8090/auth/login`, login, options);
  }

  /**
  * Fa la registrazione dell'utente
  *
  * Endpoint Rest: http://localhost:8090/auth/signup
  */
  public signup(signup: signupRequest){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    }); 
    let options = { headers: headers };
    return this.httpClient.post<signupRequest>(`http://localhost:8090/auth/signup`, signup, options);
  }

  /**
  * Scrivere qualcosa
  *
  * Endpoint Rest: scrivere qualcos'altro
  */
  /*
  getWithToken(endpoint: string): Observable<any> {
    const token = 'il-tuo-token-jwt'; // Qui dovresti ottenere il token JWT dal servizio di autenticazione
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`${this.apiUrl}/${endpoint}`, { headers });
  }
  */






  /**
  * Scrivere qualcosa
  *
  * Endpoint Rest: scrivere qualcos'altro
  */
  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }
}