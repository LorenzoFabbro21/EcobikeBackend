import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PersonalRentComponent } from './personal-rent.component';
import { ButtonModule } from 'primeng/button';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { EbikeAdModule } from 'src/app/modules/ebike-ad/ebike-ad.module';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
@NgModule({
  declarations: [PersonalRentComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, EbikeAdModule,ProgressSpinnerModule],
  exports: [PersonalRentComponent]
})
export class PersonalRentModule { }