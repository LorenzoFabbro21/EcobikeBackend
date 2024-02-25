import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EbikeComponent } from './ebike.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { AppRoutingModule } from 'src/app/app-routing.module';

@NgModule({
  declarations: [EbikeComponent],
  imports: [CommonModule, ButtonModule,CardModule,AppRoutingModule],
  exports: [EbikeComponent]
})
export class EbikeModule { }
