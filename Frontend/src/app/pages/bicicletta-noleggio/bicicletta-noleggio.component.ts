import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoggedUser } from 'src/app/classes/user';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adRent } from 'src/app/interfaces/adRent';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { Booking } from 'src/app/interfaces/booking';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-bicicletta-noleggio',
  templateUrl: './bicicletta-noleggio.component.html',
  styleUrls: ['./bicicletta-noleggio.component.scss']
})
export class BiciclettaNoleggioComponent {
  id: number = 0;
  bicicletta?: Bicicletta;
  prezzo?: number;
  prezzo_noTax?: number;
  images: string[]= [];
  imagePrincipal: string= "";
  date: Date | undefined;
  idAnnuncio?:number;
  disabledDates: Date[] = [];
  userLogged: LoggedUser | null = null;
  mostraSpinner= false;
  bikesSimili: bikeRentSell[] = [];
  bikesList: Bicicletta[] = [];
  rentList: adRent[] = [];

  showError : boolean = false;
  errorStatus: string = "";
  errorMessage: string = "";

  constructor ( private router: Router, private route: ActivatedRoute, private ebService: EcobikeApiService, private userService: UserLoggedService) {

    this.userLogged = this.userService.userLogged;
    const oggi = new Date();
    oggi.setHours(0, 0, 0, 0);
    this.disabledDates.push(oggi);

    // Genera un array di date da disabilitare (precedenti al giorno corrente)
    const datePrecedenti = this.generateDateArrayBefore(oggi);

    // Aggiunge le date al array delle date disabilitate
    this.disabledDates = this.disabledDates.concat(datePrecedenti);
  }
  ngOnInit() {
    
    this.route.queryParams.subscribe(params => {
      window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
      this.id = JSON.parse(params['idBike']);
      this.dataPage();
    });
  }

  dataPage() {
    this.bicicletta = undefined;
    this.prezzo = 0;
    this.prezzo_noTax= 0;
    this.bikesSimili = [];
    this.bikesList = [];
    this.rentList = [];
    this.images= [];
    this.imagePrincipal= "";
    this.ebService.get_bicicletta(this.id).subscribe({
      next: (response:Bicicletta) => {

        if (response != null) {
          this.bicicletta= response;
          if(this.bicicletta !== undefined && this.bicicletta.img !== undefined) {
            const splittedStrings = this.bicicletta.img.split('data:image/jpeg;base64');
            splittedStrings.forEach((image: string) => {
              if ( image !== "") {
                this.images.push('data:image/jpeg;base64'+ image);
              }
              
            });
            this.imagePrincipal = this.images[0];
            
          }
        }
      }
    });
    this.ebService.elenco_noleggi().subscribe({
      next: (response:adRent[]) => {
        if ( response) {
          response.forEach(rent => {
            if( rent.idBike == this.id) {
              this.idAnnuncio= rent.id;
              this.prezzo = rent.price;
              if ( this.prezzo) {
                const tax = (this.prezzo / 100) * 22;
                this.prezzo_noTax = this.prezzo - tax;
              }
            }
          });
        }
      }
    });
    if ( this.userService.userLogged?.token !== undefined) {
      let token: string = this.userService.userLogged?.token;
      this.ebService.get_bookings(token).subscribe({
        next: (response:Booking[]) => {
          if ( response) {
            response.forEach(booking => {
              if( booking.idAnnouncement == this.idAnnuncio && booking.startdate !== undefined) {
                this.disabledDates.push(new Date(booking.startdate));
              }
            });
          }
        }
      });
    }



    

    this.ebService.get_bicicletta(this.id).subscribe({
      next: (response: Bicicletta) => {
        this.ebService.get_similar_bike(response.type).subscribe({
          next: (response: Bicicletta[]) => {
            if(response) {
              this.bikesList = response;
              this.ebService.elenco_noleggi().subscribe({
                next: (response:adRent[]) => {
        
                  if (response) {
                    this.rentList = response;
                    
                    this.bikesList.forEach(bike => {
                      this.rentList.forEach(sell => {
                        if(sell.idBike == bike.id && sell.idUser != this.userLogged?.id) {
                          const obj: bikeRentSell= {
                            bike: bike,
                            price: sell.price ? sell.price : 0
                          };
                          this.bikesSimili.push(obj);
                        }
                      });
                    });
    
                    this.bikesSimili.forEach((bike, index) => {
                      if(bike.bike.id == this.id)
                        this.bikesSimili.splice(index, 1);
                    })
                  }
                }
              });
            }
          }
        });
      }
    });
  }


  private generateDateArrayBefore(date: Date): Date[] {
    const oggi = new Date();
    oggi.setHours(0, 0, 0, 0);
    const dateArray: Date[] = [];
  
    // Imposta la data all'inizio del mese corrente
    date.setDate(1);
  
    
  
    // Genera date dal primo giorno al giorno corrente
    while (date.getDate() > 0 && date < oggi) {
      dateArray.push(new Date(date));
      date.setDate(date.getDate() + 1);
    }
  
    return dateArray;
  }

  prenota() {

    if ( this.date == undefined) {
      this.errorStatus= "400";
      this.errorMessage = "Inserire la data del noleggio";
      this.showError = true;
      return; 
    }
    const booking: Booking = {
      idPrivate : this.userService.userLogged?.id,
      idAnnouncement: this.idAnnuncio,
      startdate : this.date,
      enddate: this.date
    }
    if ( this.userService.userLogged?.token !== undefined) {
      let token: string = this.userService.userLogged?.token;
      this.mostraSpinner= true;
      this.ebService.new_booking(booking, token).subscribe({
        next: (response:Booking) => {
          if ( response) {
            console.log(response);
            setTimeout(() => {
              this.mostraSpinner = false;
              this.router.navigate(['/']);
            }, 3500);
          }
        }
      });
    }
    

    return;
  }

  imageActualChange(image: string) {
    this.imagePrincipal = image;
  }

  changeValue (event : boolean | any) :void {
    this.showError = event;
  }
}
