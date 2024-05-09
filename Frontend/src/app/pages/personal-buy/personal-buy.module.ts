import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PersonalBuyComponent } from './personal-buy.component';
import { ButtonModule } from 'primeng/button';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { EbikeAdModule } from 'src/app/modules/ebike-ad/ebike-ad.module';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
@NgModule({
  declarations: [PersonalBuyComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, EbikeAdModule,ProgressSpinnerModule],
  exports: [PersonalBuyComponent]
})
export class PersonalBuyModule { }