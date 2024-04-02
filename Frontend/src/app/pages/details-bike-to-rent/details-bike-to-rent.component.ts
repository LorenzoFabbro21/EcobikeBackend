import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { adRent } from 'src/app/interfaces/adRent';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

@Component({
  selector: 'app-details-bike-to-rent',
  templateUrl: './details-bike-to-rent.component.html',
  styleUrls: ['./details-bike-to-rent.component.scss']
})
export class DetailsBikeToRentComponent {

  typeAd: string="";
  id: number = 0;
  bicicletta?: Bicicletta;
  prezzo?: number;
  prezzo_noTax?: number;
  images: string[]= [];
  imagePrincipal: string= "";
  idAnnuncio?: number
  constructor ( private route: ActivatedRoute, private ebService: EcobikeApiService) {
    
  }

  ngOnInit(): void {
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
            this.ebService.elenco_vendite().subscribe({
              next: (response:adRent[]) => {
                if ( response) {
                  response.forEach(rent => {
                    if( rent.idBike == this.bicicletta?.id) {
                      this.idAnnuncio= rent.id;
                      this.prezzo = rent.price;
                    }
                    if ( this.prezzo) {
                      const tax = (this.prezzo / 100) * 22;
                      this.prezzo_noTax = this.prezzo - tax;
                    }
                  });
                }
              }
            });
          }
        }
      }
    });



    
  }

  imageActualChange(image: string) {
    this.imagePrincipal = image;
  }
}
