import { Component } from '@angular/core';
import { ActivatedRoute, } from '@angular/router';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { Bicicletta } from 'src/app/interfaces/bicicletta';

@Component({
  selector: 'app-bicicletta-dettagli',
  templateUrl: './bicicletta-dettagli.component.html',
  styleUrls: ['./bicicletta-dettagli.component.scss']
})
export class BiciclettaDettagliComponent {

  bicicletta?: Bicicletta;
  prezzo?: number;
  prezzo_noTax?: number;
  bikesSimili: Bicicletta[]= [];
  images: string[]= [];
  constructor ( private route: ActivatedRoute) {

    this.images = ['ebike.jpg','ebike-2.jpg','ebike.jpg','ebike.jpg','ebike.jpg']; 
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
    this.route.queryParams.subscribe(params => {
      this.bicicletta = JSON.parse(params['ebike']);
      this.prezzo = params["prezzo"];
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
    if (this.bicicletta) {
      this.bicicletta.img = image;
    }
    
    

  }
}
