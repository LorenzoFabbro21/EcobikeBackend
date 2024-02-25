import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page.component';
import { ButtonModule } from 'primeng/button';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { EbikeModule } from 'src/app/modules/ebike/ebike.module';
@NgModule({
  declarations: [HomePageComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, EbikeModule],
  exports: [HomePageComponent]
})
export class HomePageModule { }
