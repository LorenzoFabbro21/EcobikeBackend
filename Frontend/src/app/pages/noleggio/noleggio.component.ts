import { Component } from '@angular/core';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adRent } from 'src/app/interfaces/adRent';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

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
  rents: adRent[]= [];
  bikeRentPrice: bikeRentSell[] = [];
  mostraSpinner: boolean= true;
  spinnerFilter: boolean= false;
  bikesNoleggioBackup: Bicicletta[] = [];
  bikesNull = false;

  constructor (private ebService: EcobikeApiService) {

    this.ebService.elenco_noleggi().subscribe({
      next: (response:adRent[]) => {

        if (response.length != 0) {
          this.rents = response;
          this.ebService.elenco_bici_noleggio().subscribe({
            next: (response:Bicicletta[]) => {
      
              if (response.length !=0 && response != null) {
                this.bikesNoleggio= response;
                this.rents.forEach(rent => {
                  this.bikesNoleggio.forEach(bike => {
                    if(rent.idBike == bike.id) {
                      const obj: bikeRentSell= {
                        bike: bike,
                        price: rent.price ? rent.price : 0
                      };
                      this.bikeRentPrice.push(obj);
                    }
                  });
                  
                });
                this.createFilters();
                this.mostraSpinner = false;
              }
              else {
                this.mostraSpinner = false;
                this.bikesNull = true;
              }

            }
          });
        }
        else {
          this.mostraSpinner = false;
          this.bikesNull = true;
        }

      }
    });


   
  }
  createFilters() {
    const noneMarca = { name: '', code: undefined };
    this.marcaFiltered.push(noneMarca);
    const noneTaglia = { name: '', code: undefined };
    this.tagliaFiltered.push(noneTaglia);
    const noneColore = { name: '', code: undefined };
    this.coloreFiltered.push(noneColore);
    this.bikesNoleggio.forEach(bike => {
      const brandValue =  { name: bike.brand, code: bike.brand };
      const brandExists = this.marcaFiltered.some(item => item.name === brandValue.name && item.code === brandValue.code);
      if(!brandExists) {
        this.marcaFiltered.push(brandValue);
      }
      const sizeValue =  { name: bike.size, code: bike.size };
      const tagliaExists = this.tagliaFiltered.some(item => item.name === sizeValue.name && item.code === sizeValue.code);
      if(!tagliaExists) {
        this.tagliaFiltered.push(sizeValue);
      }
      const colorValue =  { name: bike.color, code: bike.color };
      const coloreExists = this.coloreFiltered.some(item => item.name === colorValue.name && item.code === colorValue.code);
      if(!coloreExists) {
        this.coloreFiltered.push(colorValue);
      }
    });

    this.marcaList=this.marcaFiltered;
    this.tagliaList= this.tagliaFiltered;
    this.coloreList= this.coloreFiltered;
    
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

  setBrandValue(obj:any) {
    this.marcaValue= obj.value.code;
    this.getBikesFiltered();
  }

  setColorValue(obj:any) {
    this.coloreValue= obj.value.code;
    this.getBikesFiltered();
  }

  setSizeValue(obj:any) {
    this.tagliaValue= obj.value.code;
    this.getBikesFiltered();
  }
  
  getBikesFiltered(){
    this.spinnerFilter = true;
    this.ebService.findFilteredBikes(this.marcaValue,this.coloreValue,this.tagliaValue).subscribe({
      next: (filteredBikes:Bicicletta[]) => {
        this.bikeRentPrice.splice(0,this.bikeRentPrice.length);
        if (filteredBikes != null) {
          this.rents.forEach(rent => {
            filteredBikes.forEach(bike => {
              if(rent.idBike == bike.id) {
                const obj: bikeRentSell= {
                  bike: bike,
                  price: rent.price ? rent.price : 0
                };
                this.bikeRentPrice.push(obj);
              }
            });
          });
          this.spinnerFilter = false;
        }
      }
    })
  }
  
  viewAll() {
    this.viewAllBikes = true;
  }
    

}
