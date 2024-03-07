import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adRent } from 'src/app/interfaces/adRent';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { Booking } from 'src/app/interfaces/booking';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

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
  bikesSimili: Bicicletta[]= [];
  images: string[]= [];
  imagePrincipal: string= "";
  date: Date | undefined;
  idAnnuncio?:number;
  disabledDates: Date[] = [];
  constructor ( private route: ActivatedRoute, private ebService: EcobikeApiService) {

    const oggi = new Date();
    oggi.setHours(0, 0, 0, 0);
    this.disabledDates.push(oggi);

    // Genera un array di date da disabilitare (precedenti al giorno corrente)
    const datePrecedenti = this.generateDateArrayBefore(oggi);

    // Aggiunge le date al array delle date disabilitate
    this.disabledDates = this.disabledDates.concat(datePrecedenti);

    this.bikesSimili= [
      {
      id: 1,
      model: 'RX1-Sport',
      brand: 'Olmo',
      color: 'Rosso e bianco',
      size: Taglia.TagliaS,
      type: 'Mountain Bike',
      img:  'ebike.jpg'
      },
      {
        id: 2,
        model:'CV5-Sport',
        brand:'Thor',
        color: 'Rosso e nero',
        size:  Taglia.TagliaM,
        type: 'Mountain Bike',
        img: 'ebike.jpg'
        },
        {
          id: 3,
          model:'BN8-Trial',
          brand:'Prova',
          color: 'Rosso e bianco',
          size:  Taglia.TagliaS,
          type: 'Trial',
          img: 'ebike.jpg'
          },
          {
            id: 4,
            model: 'TopModel',
            brand: 'Brabus',
            color: 'Verde',
            size: Taglia.TagliaL,
            type: 'Mountain Bike',
            img: 'ebike.jpg'
            }
    ];
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
            }
          });
        }
      }
    });

    this.ebService.get_bookings().subscribe({
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



    if ( this.prezzo) {
      const tax = (this.prezzo / 100) * 22;
      this.prezzo_noTax = this.prezzo - tax;
    }
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
    console.log(this.date);

    
    const booking: Booking = {
      idPrivate : 1,
      idAnnouncement: this.idAnnuncio,
      startdate : this.date,
      enddate: this.date
    }

    this.ebService.new_booking(booking).subscribe({
      next: (response:Booking) => {
        if ( response) {
          console.log(response);
        }
      }
    });

    //const giorno = this.date?.toDateString();
  

    /* console.log(giorno);
    if( giorno!== undefined) {
      const dataDaStringa = new Date(giorno);
      console.log(dataDaStringa)
    } */
    
    return;
  }

  imageActualChange(image: string) {
    this.imagePrincipal = image;
  }
}
