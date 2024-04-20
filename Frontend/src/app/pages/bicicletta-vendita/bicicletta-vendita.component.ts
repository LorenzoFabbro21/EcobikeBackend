import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Scroll, } from '@angular/router';
import { LoggedUser } from 'src/app/classes/user';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adSell } from 'src/app/interfaces/adSell';
import { Appointment } from 'src/app/interfaces/appointment';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-bicicletta-vendita',
  templateUrl: './bicicletta-vendita.component.html',
  styleUrls: ['./bicicletta-vendita.component.scss']
})
export class BiciclettaVenditaComponent implements OnInit{

  id: number = 0;
  bicicletta?: Bicicletta;
  prezzo?: number;
  prezzo_noTax?: number;
  bikesSimili: bikeRentSell[] = [];
  bikesList: Bicicletta[] = [];
  sellList: adSell[] = [];
  images: string[]= [];
  imagePrincipal: string= "";
  idAnnuncio?: number;
  userLogged: LoggedUser | null = null;
  mostraSpinner: boolean = false;
  disabledDates: Date[] = [];
  date: Date | undefined;
  brand: string | undefined;

  constructor ( private route: ActivatedRoute, private ebService: EcobikeApiService, private userService: UserLoggedService, private router: Router) {
    
    this.userLogged = this.userService.userLogged;
    
    const oggi = new Date();
    oggi.setHours(0, 0, 0, 0);
    this.disabledDates.push(oggi);
    const datePrecedenti = this.generateDateArrayBefore(oggi);
    this.disabledDates = this.disabledDates.concat(datePrecedenti);
    
  }

  ngOnInit() {
    window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
    this.route.queryParams.subscribe(params => {
      this.id = JSON.parse(params['idBike']);
    });

    this.ebService.get_bicicletta(this.id).subscribe({
      next: (response:Bicicletta) => {

        if (response != null) {
          this.bicicletta= response;
          this.brand = this.bicicletta?.brand;
          if(this.bicicletta !== undefined && this.bicicletta.img !== undefined) {
            const splittedStrings = this.bicicletta.img.split('data:image/jpeg;base64');
            splittedStrings.forEach((image: string) => {
              if (image !== "") {
                this.images.push('data:image/jpeg;base64'+ image);
              }
              
            });
            this.imagePrincipal = this.images[0];
            
          }
        }
      }
    });

    this.ebService.elenco_vendite().subscribe({
      next: (response:adSell[]) => {
        if ( response) {
          response.forEach(rent => {
            if( rent.idBike == this.bicicletta?.id) {
              this.idAnnuncio= rent.id;
              this.prezzo = rent.price;
            }
          });
        }
      }
    });


    this.ebService.get_appointments().subscribe({
      next: (response:Appointment[]) => {
        if ( response) {
          response.forEach(appointment => {
            if( appointment.idAnnouncement == this.idAnnuncio && appointment.date !== undefined) {
              this.disabledDates.push(new Date(appointment.date));
            }
          });
        }
      }
    });

    if ( this.prezzo) {
      const tax = (this.prezzo / 100) * 22;
      this.prezzo_noTax = this.prezzo - tax;
    }


    this.ebService.get_bicicletta(this.id).subscribe({
      next: (response:Bicicletta) => {
        this.ebService.get_similar_bike(response.brand).subscribe({
          next: (response: Bicicletta[]) => {
            if(response) {
              this.bikesList = response;
    
              this.ebService.elenco_vendite().subscribe({
                next: (response:adSell[]) => {
        
                  if (response) {
                    this.sellList = response;
                    
                    this.bikesList.forEach(bike => {
                      this.sellList.forEach(sell => {
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
    this.mostraSpinner = true;
    let appointment : Appointment;
    let d = new Date();
    let id = this.userService.userLogged?.id;
    
    this.ebService.elenco_vendite().subscribe({
      next: (response:adSell[]) => {
        if ( response) {
          response.forEach(sell => {
            if( sell.idBike == this.bicicletta?.id) {
              this.idAnnuncio= sell.id;


              appointment = {
                idUser: id,
                idAnnouncement: this.idAnnuncio,
                date: d
              }
          
          
              if( this.userService.userLogged?.token !== undefined) {
                let token : string = this.userService.userLogged?.token;
                this.ebService.new_appointment(appointment, token).subscribe({
                  next: (response: Appointment) => {
                  if( response && response.id) {
                    console.log(response);
                  }
                }
                });
              }
            }
         });
        }
      }
    });

    
  }

  imageActualChange(image: string) {
    this.imagePrincipal = image;
  }
}
