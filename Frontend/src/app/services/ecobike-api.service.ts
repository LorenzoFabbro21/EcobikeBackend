import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Bicicletta } from '../interfaces/bicicletta';
import { Observable } from 'rxjs/internal/Observable';
import { catchError, retry, throwError } from 'rxjs';
import { adRent } from '../interfaces/adRent';
import { adSell } from '../interfaces/adSell';

@Injectable({
  providedIn: 'root'
})
export class EcobikeApiService {

  constructor(protected httpClient: HttpClient) { }

  url="http://localhost:8080/api"


  /**
 * Restituisce l'elenco delle biciclette a noleggio
 *
 * Endpoint Rest: bicicletta/noleggio
 */
   public elenco_bici_noleggio (): Observable<Bicicletta[]> {
    return this.httpClient.get<Bicicletta[]>(`${this.url}/adrent/bikes`);
  }


    /**
 * Restituisce l'elenco delle biciclette a noleggio
 *
 * Endpoint Rest: adsell/bikes
 */
    public elenco_bici_vendita (): Observable<Bicicletta[]> {
      return this.httpClient.get<Bicicletta[]>(`${this.url}/adsell/bikes`);
    }

/**
 * Restitusice la bicicletta selezionata
 *
 * Endpoint Rest: bike/{bikeNo}
 */
  public get_bicicletta (bikeNo: number): Observable<Bicicletta> {
    return  this.httpClient.get<Bicicletta>(`${this.url}/bike/${bikeNo}`);
  }


  public get_biciclette (): Observable<Bicicletta[]> {
    return  this.httpClient.get<Bicicletta[]>(`${this.url}/bike`);
  }

/**
 * Inserisce una nuova bicicletta
 *
 * Endpoint Rest: bike
 */
  public new_bike(bike: Bicicletta | any){
    console.log("suka5");
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    return this.httpClient.post<Bicicletta>(`${this.url}/bike`, bike, options);
  }
/**
 * Inserisce una nuovo noleggio
 *
 * Endpoint Rest: bike
 */
  public new_noleggio(adRent: adRent | any){

    return this.httpClient.post<adRent>(`${this.url}/adrent`, adRent, {
        headers: new HttpHeaders({
          'Access-Control-Allow-Origin': '*'
        })
      }).pipe(retry(0), catchError(this.handleError)
      );
  }

  /**
 * Inserisce una nuovo noleggio
 *
 * Endpoint Rest: bike
 */
  public new_vendita(adSell: adSell | any){

    return this.httpClient.post<adSell>(`${this.url}/adsell`, adSell, {
        headers: new HttpHeaders({
          'Access-Control-Allow-Origin': '*'
        })
      }).pipe(retry(0), catchError(this.handleError)
      );
  }
  



 

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



