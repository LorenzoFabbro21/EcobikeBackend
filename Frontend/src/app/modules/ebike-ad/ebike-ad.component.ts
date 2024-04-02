import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { User } from 'src/app/classes/user';
import { Taglia } from 'src/app/enum/tagliaEnum';
import { adRent } from 'src/app/interfaces/adRent';
import { adSell } from 'src/app/interfaces/adSell';
import { Appointment } from 'src/app/interfaces/appointment';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { Booking } from 'src/app/interfaces/booking';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';

@Component({
  selector: 'app-ebike-ad',
  templateUrl: './ebike-ad.component.html',
  styleUrls: ['./ebike-ad.component.scss']
})
export class EbikeAdComponent implements OnInit{

  @Input()
    bike?: Bicicletta

  @Input()
    user?: User

  @Input() 
    appointment?: Appointment

  @Input() 
    booking?: Booking

  @Input()
    ad?: adRent | adSell


  firstImage:string = "";
  constructor ( private router: Router, private ebService: EcobikeApiService) {

  }

  ngOnInit(): void {
    if(this.bike!== undefined && this.bike.img !== undefined){
      const splittedStrings = this.bike.img.split('data:image/jpeg;base64');
      const images : string[] = [];
      splittedStrings.forEach((image: string) => {
        if ( image !== "") {
          images.push('data:image/jpeg;base64'+ image);
        }    
      });
      this.firstImage = images[0];
    } 
  }

  clickBike() {
    const navigationExtras: NavigationExtras = {
      queryParams:{
        idBike: this.bike?.id
      }
    };
      this.router.navigate(['/details-bike-to-rent'], navigationExtras);
  }
}