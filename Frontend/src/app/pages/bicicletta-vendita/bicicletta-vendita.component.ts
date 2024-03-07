import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Scroll, } from '@angular/router';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

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
  bikesSimili: Bicicletta[]= [];
  images: string[]= [];
  imagePrincipal: string= "";
  idAnnuncio?: number
  constructor ( private route: ActivatedRoute, private ebService: EcobikeApiService) {
    
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
      this.prezzo = JSON.parse(params['price']);
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

    if ( this.prezzo) {
      const tax = (this.prezzo / 100) * 22;
      this.prezzo_noTax = this.prezzo - tax;
    }
  }

  prenota() {
    return;
  }

  imageActualChange(image: string) {
    this.imagePrincipal = image;
  }
}
