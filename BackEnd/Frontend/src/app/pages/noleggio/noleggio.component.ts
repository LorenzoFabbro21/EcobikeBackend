import { Component } from '@angular/core';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { Bicicletta } from 'src/app/interfaces/bicicletta';

interface AutoCompleteCompleteEvent {
  originalEvent: Event| any;
  query: any;
}

@Component({
  selector: 'app-noleggio',
  templateUrl: './noleggio.component.html',
  styleUrls: ['./noleggio.component.scss']
})
export class NoleggioComponent {
  viewAllBikes: boolean = false;
  marcaValue?: any;
  marcaFiltered: any[] = [];
  marcaList: any[]= [];

  tagliaValue?: any;
  tagliaFiltered: any[] = [];
  tagliaList: any[]= [];

  coloreValue?: any;
  coloreFiltered: any[] = [];
  coloreList: any[]= [];

  bikesNoleggio: Bicicletta[] = [];

  constructor () {

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
            },
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


    this.marcaList = [
      { name: 'Afghanistan', code: 'AF' },
            { name: 'Albania', code: 'AL' },
            { name: 'Algeria', code: 'DZ' },
            { name: 'American Samoa', code: 'AS' },
            { name: 'Andorra', code: 'AD' },
            { name: 'Angola', code: 'AO' },
            { name: 'Anguilla', code: 'AI' },
            { name: 'Antarctica', code: 'AQ' },
            { name: 'Antigua and Barbuda', code: 'AG' },
            { name: 'Argentina', code: 'AR' },
            { name: 'Armenia', code: 'AM' },
            { name: 'Aruba', code: 'AW' },
            { name: 'Australia', code: 'AU' }
    ];

    this.marcaFiltered= this.marcaList;
  }


  filterMarca(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.marcaList as any[]).length; i++) {
        let marca = (this.marcaList as any[])[i];
        if (marca.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
            filtered.push(marca);
        }
    }
    this.marcaFiltered = filtered;
  }

  filterTaglia(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.tagliaList as any[]).length; i++) {
        let taglia = (this.tagliaList as any[])[i];
        if (taglia.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
            filtered.push(taglia);
        }
    }
    this.tagliaFiltered = filtered;
  }

  filterColore(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.marcaList as any[]).length; i++) {
        let colore = (this.coloreList as any[])[i];
        if (colore.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
            filtered.push(colore);
        }
    }
    this.coloreFiltered = filtered;
  }

  viewAll() {
    this.viewAllBikes = true;
  }
    

}
