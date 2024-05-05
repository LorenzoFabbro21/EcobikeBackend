import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { adSell } from 'src/app/interfaces/adSell';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';

@Component({
  selector: 'app-dettagli-bike-to-sell',
  templateUrl: './dettagli-bike-to-sell.component.html',
  styleUrls: ['./dettagli-bike-to-sell.component.scss']
})
export class DettagliBikeToSellComponent implements OnInit{
  typeAd: string="";
  id: number = 0;
  bicicletta?: Bicicletta;
  prezzo?: number;
  prezzo_noTax?: number;
  images: string[]= [];
  imagePrincipal: string= "";
  idAnnuncio?: number
  constructor (private router: Router, private route: ActivatedRoute, private ebService: EcobikeApiService, private userService: UserLoggedService) {
    if ( this.userService.userLogged?.id == undefined && this.userService.userLogged?.token == undefined) {
      this.router.navigate(['/']);
    }
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
              next: (response:adSell[]) => {
                if ( response) {
                  response.forEach(sell => {
                    if( sell.idBike == this.bicicletta?.id) {
                      this.idAnnuncio= sell.id;
                      this.prezzo = sell.price;
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
