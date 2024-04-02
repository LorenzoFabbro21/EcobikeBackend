import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShopsComponent } from './shops.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';
import { ShopModule } from 'src/app/modules/shop/shop.module';
import { FooterModule } from 'src/app/modules/footer/footer.module';
import { HeaderModule } from 'src/app/modules/header/header.module';

@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [ShopsComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule,ProgressSpinnerModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule, CardModule, AccordionModule, ShopModule, FooterModule, HeaderModule],
  exports: [ShopsComponent]
})
export class ShopsModule{
 }