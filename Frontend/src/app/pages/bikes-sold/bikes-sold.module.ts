import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BikesSoldComponent } from './bikes-sold.component';
import { ButtonModule } from 'primeng/button';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { EbikeAdModule } from 'src/app/modules/ebike-ad/ebike-ad.module';
@NgModule({
  declarations: [BikesSoldComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, EbikeAdModule],
  exports: [BikesSoldComponent]
})
export class BikesSoldModule { }
