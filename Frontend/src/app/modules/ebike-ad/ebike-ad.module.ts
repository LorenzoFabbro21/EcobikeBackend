import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { EbikeAdComponent } from './ebike-ad.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { AppRoutingModule } from 'src/app/app-routing.module';

@NgModule({
  declarations: [EbikeAdComponent],
  imports: [CommonModule, ButtonModule,CardModule,AppRoutingModule],
  providers: [DatePipe],
  exports: [EbikeAdComponent]
})
export class EbikeAdModule { }
