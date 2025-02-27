import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { LoggedUser } from 'src/app/classes/user';
import { Tipologia } from 'src/app/enum/tipologiaEnum';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adRent } from 'src/app/interfaces/adRent';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';
import { HttpErrorResponse } from '@angular/common/http';

interface AutoCompleteCompleteEvent {
  originalEvent: Event| any;
  query: any;
}

interface UploadEvent {
  originalEvent: Event;
  files: File[];
}


@Component({
  selector: 'app-form-vendita-noleggio',
  templateUrl: './form-noleggio.component.html',
  styleUrls: ['./form-noleggio.component.scss']
})
export class FormNoleggioComponent {
  uploadedFiles: any[] = [];
  userLogged?: LoggedUser;
  tagliaValue!: any;
  tagliaFiltered: any[] = [];
  tagliaList: any[]= [];
  colore!:string;
  marca!:string;
  model!:string;
  tipologiaValue!:any;
  tipologiaFiltered: any[] = [];
  tipologiaList: any[]= [];
  prezzo!:number;
  info!:string;
  img?:string= "";
  mostraSpinner: boolean = false;

  showError : boolean = false;
  errorStatus: string = "";
  errorMessage: string = "";

  constructor (private router: Router, private ebService: EcobikeApiService, private userService : UserLoggedService) {
    if ( this.userService.userLogged?.id == undefined && this.userService.userLogged?.token == undefined) {
      this.router.navigate(['/']);
    } 
  
    this.tagliaList = [
      { name: 'S', code: Taglia.TagliaS },
      { name: 'M', code: Taglia.TagliaM },
      { name: 'L', code: Taglia.TagliaL }
    ];

    this.tipologiaList = [
      { name: 'City', code: Tipologia.City },
      { name: 'Racing', code: Tipologia.Racing },
      { name: 'Gravel', code: Tipologia.Gravel },
      { name: 'Mountain Bike', code: Tipologia.Mountain_Bike },
      { name: 'Foldable', code: Tipologia.Foldable }
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

  filterTipologia(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.tipologiaList as any[]).length; i++) {
        let tipologia = (this.tipologiaList as any[])[i];
        if (tipologia.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
            filtered.push(tipologia);
        }
    }
    this.tipologiaFiltered = filtered;
  }

  onUpload(event:UploadEvent | any ) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }
  }

  send () {
    this.img = "";
    const reader = new FileReader();
    let count = 0;
    const readNextFile = () => {
      if (count < this.uploadedFiles.length) {
        const file = this.uploadedFiles[count];
        reader.onload = (e) => {
          const base64String = (e.target as any).result;
          this.img= this.img + base64String;
          count++;
          readNextFile(); // Leggi il prossimo file in modo ricorsivo
        };

        reader.readAsDataURL(file);
      } else {
      this.postBike(); 
      }
  }

  readNextFile();
}

  postBike() {

    this.mostraSpinner= true;
    let idBike: number;
    let bike: Bicicletta;
    
    let size;
    if (this.tagliaValue !== undefined && this.tagliaValue.code !== undefined) {
      size=  this.tagliaValue.code;
    } else {
      size = null;
    }
    let type;
    if (this.tipologiaValue !== undefined && this.tipologiaValue.code !== undefined) {
      type= this.tipologiaValue.code;
    } else {
      type= null;
    }
    bike = {
      model: this.model,
      brand: this.marca,
      color: this.colore,
      size: size,
      type: type,
      info: this.info,
      img: this.img
    }

    let adRent: adRent;
    adRent = {
    price:this.prezzo ?? 0,
    idBike:0,
    idUser: this.userService.userLogged?.id
    }
    console.log(bike)
    console.log(adRent)

    if( this.userService.userLogged?.token !== undefined) {
      let token : string = this.userService.userLogged?.token;
      this.ebService.new_bike_rent(bike, adRent, token).subscribe({
        next: (response) => {
          if(response) {
            setTimeout(() => {
              this.mostraSpinner = false;
              this.router.navigate(['/']);
            }, 3500);
          }
        },error: (error: HttpErrorResponse) => {
          if (error.status === 404) {
            this.errorStatus = "Error:" + error.status.toString();
            const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
            if( errorMessageParts.length == 1) {
              this.errorMessage = errorMessageParts[0];
            }
            else {
              const errorMessage = errorMessageParts.slice(1).join(':').trim();
              this.errorMessage = errorMessage;
            }
            
            this.showError = true;
            this.mostraSpinner = false;
          } else if (error.status === 400) {
            this.errorStatus = "Error:" + error.status.toString();
            const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
            if( errorMessageParts.length == 1) {
              this.errorMessage = errorMessageParts[0];
            }
            else {
              const errorMessage = errorMessageParts.slice(1).join(':').trim();
              this.errorMessage = errorMessage;
            }
            this.mostraSpinner = false;
            this.showError = true;
          } else {
            this.errorStatus = "Error:" + error.status.toString();
            const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
            if( errorMessageParts.length == 1) {
              this.errorMessage = errorMessageParts[0];
            }
            else {
              const errorMessage = errorMessageParts.slice(1).join(':').trim();
              this.errorMessage = errorMessage;
            }
            this.mostraSpinner = false;
            this.showError = true;
          }
        }
      });
    }
  }
  changeValue (event : boolean | any) :void {
    this.showError = event;
  }
}
