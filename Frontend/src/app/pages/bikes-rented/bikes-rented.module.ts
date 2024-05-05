import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BikesRentedComponent } from './bikes-rented.component';
import { ButtonModule } from 'primeng/button';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { EbikeAdModule } from 'src/app/modules/ebike-ad/ebike-ad.module';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
@NgModule({
  declarations: [BikesRentedComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, EbikeAdModule,ProgressSpinnerModule],
  exports: [BikesRentedComponent]
})
export class BikesRentedModule { }