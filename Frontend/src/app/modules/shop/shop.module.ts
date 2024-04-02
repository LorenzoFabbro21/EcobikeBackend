import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShopComponent } from './shop.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { AppRoutingModule } from 'src/app/app-routing.module';

@NgModule({
  declarations: [ShopComponent],
  imports: [CommonModule, ButtonModule,CardModule,AppRoutingModule],
  exports: [ShopComponent]
})
export class ShopModule { }
