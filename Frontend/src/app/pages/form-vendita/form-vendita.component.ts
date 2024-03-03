import { Component } from '@angular/core';
import { LoggedUser } from 'src/app/classes/user';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

interface AutoCompleteCompleteEvent {
  originalEvent: Event| any;
  query: any;
}

interface UploadEvent {
  originalEvent: Event;
  files: File[];
}

@Component({
  selector: 'app-form-vendita',
  templateUrl: './form-vendita.component.html',
  styleUrls: ['./form-vendita.component.scss']
})
export class FormVenditaComponent {
  uploadedFiles: any[] = [];
  userLogged?: LoggedUser;
  tagliaValue!: Taglia;
  tagliaFiltered: any[] = [];
  tagliaList: any[]= [];
  colore!:string;
  marca!:string;
  model!:string;
  tipologia!:string;
  prezzo!:number;
  misure!:string;
  img?:any;
  mostraSpinner: boolean= false;

  constructor ( private ebService: EcobikeApiService) {
    
    /* if ( userService.userLogged ) {
      this.userLogged = userService.userLogged;
    } */
    this.tagliaList = [
      { name: 'S', code: Taglia.TagliaS },
      { name: 'M', code: Taglia.TagliaM },
      { name: 'L', code: Taglia.TagliaL }
    ];
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

  onUpload(event:UploadEvent) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }
  }

  send () {
    
    const reader = new FileReader();

    reader.onload = (e) => {
      const base64String = (e.target as any).result;
      this.img= base64String;
      this.postBike();
    };

    reader.readAsDataURL(this.uploadedFiles[0]);

  }

  postBike() {
    this.mostraSpinner = true;
    let idBike: number;
    let bike: Bicicletta;
    bike = {
      model: this.model,
      brand: this.marca,
      color: this.colore,
      size: Taglia.TagliaS,
      type: this.tipologia,
      measure: this.misure,
      img: this.img
    }

    this.ebService.new_bike(bike).subscribe(response=>{
      if( response && response.id) {
        idBike = response.id;

        let adSell: adSell;
        adSell = {
        price:this.prezzo,
        idBike:idBike
        }
        this.ebService.new_vendita(adSell).subscribe({
          next: (response:adSell) => {
            console.log(response);
            
            setTimeout(() => {
              this.mostraSpinner = false;
              window.location.reload();
            }, 3500);
        }
        });
      }

    });
    

  }
}
