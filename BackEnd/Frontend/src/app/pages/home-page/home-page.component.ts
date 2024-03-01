import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { adRent } from 'src/app/interfaces/adRent';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent {
  bikesNoleggio: Bicicletta[] = [];
  bikesVendita: Bicicletta[] = [];

  constructor (private ebService: EcobikeApiService) {

    this.ebService.elenco_bici_noleggio().subscribe({
      next: (response:Bicicletta[]) => {

        if (response != null) {
          this.bikesNoleggio= response;
        }
      }
    });

    this.ebService.elenco_bici_vendita().subscribe({
      next: (response:Bicicletta[]) => {

        if (response != null) {
          this.bikesVendita= response;
        }
      }
    });
    /* this.bikesNoleggio= [
      {
      id: 1,
      model: 'RX1-Sport',
      marca: 'Olmo',
      colore: 'Rosso e bianco',
      taglia: Taglia.TagliaS,
      tipologia: 'Mountain Bike',
      immagini: 'ebike.jpg'
      },
      {
        id: 2,
        model: 'CV5-Sport',
        marca: 'Thor',
        colore: 'Rosso e nero',
        taglia: Taglia.TagliaM,
        tipologia: 'Mountain Bike',
        immagini: 'ebike.jpg'
        },
        {
          id: 3,
          model: 'BN8-Trial',
          marca: 'Prova',
          colore: 'Rosso e bianco',
          taglia: Taglia.TagliaS,
          tipologia: 'Trial',
          immagini: 'ebike.jpg'
          },
          {
            id: 4,
            model: 'TopModel',
            marca: 'Brabus',
            colore: 'Verde',
            taglia: Taglia.TagliaL,
            tipologia: 'Mountain Bike',
            immagini: 'ebike.jpg'
            }
    ]; */







    /* this.bikesVendita= [
      {
      id: 1,
      model: 'RX1-Sport',
      marca: 'Olmo',
      colore: 'Rosso e bianco',
      taglia: Taglia.TagliaS,
      tipologia: 'Mountain Bike',
      immagini: 'ebike.jpg'
      },
      {
        id: 2,
        model: 'CV5-Sport',
        marca: 'Thor',
        colore: 'Rosso e nero',
        taglia: Taglia.TagliaM,
        tipologia: 'Mountain Bike',
        immagini: 'ebike.jpg'
        },
        {
          id: 3,
          model: 'BN8-Trial',
          marca: 'Prova',
          colore: 'Rosso e bianco',
          taglia: Taglia.TagliaS,
          tipologia: 'Trial',
          immagini: 'ebike.jpg'
          },
          {
            id: 4,
            model: 'TopModel',
            marca: 'Brabus',
            colore: 'Verde',
            taglia: Taglia.TagliaL,
            tipologia: 'Mountain Bike',
            immagini: 'ebike.jpg'
            },
            {
              id: 5,
              model: 'Prova5',
              marca: 'Ciao',
              colore: 'Verde',
              taglia: Taglia.TagliaL,
              tipologia: 'Mountain Bike',
              immagini: 'ebike.jpg'
              }
        ];
   */
  }
}
