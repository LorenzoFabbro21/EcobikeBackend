import { Component } from '@angular/core';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

interface AutoCompleteCompleteEvent {
  originalEvent: Event| any;
  query: any;
}

@Component({
  selector: 'app-vendita',
  templateUrl: './vendita.component.html',
  styleUrls: ['./vendita.component.scss']
})
export class VenditaComponent {
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

  bikesVendita: Bicicletta[] = [];
  sells: adSell[]= [];
  bikeSellPrice:bikeRentSell[]= [];
  mostraSpinner:boolean = true;

  constructor (private ebService: EcobikeApiService) {

    this.ebService.elenco_vendite().subscribe({
      next: (response:adSell[]) => {

        if (response != null) {
          this.sells = response
          this.ebService.elenco_bici_vendita().subscribe({
            next: (response:Bicicletta[]) => {
      
              if (response != null) {
                this.bikesVendita= response;
      
                this.sells.forEach(rent => {
                  this.bikesVendita.forEach(bike => {
                    if(rent.idBike == bike.id) {
                      const obj: bikeRentSell= {
                        bike: bike,
                        price: rent.price ? rent.price : 0
                      };
                      this.bikeSellPrice.push(obj);
                    }
                  });
                });
                this.mostraSpinner = false;
              }
            }
          });
        }
      }
    });

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
