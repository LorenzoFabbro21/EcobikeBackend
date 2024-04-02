import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DettagliBikeToSellComponent } from './dettagli-bike-to-sell.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FileUploadModule } from 'primeng/fileupload';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { EbikeModule } from 'src/app/modules/ebike/ebike.module';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';

@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [DettagliBikeToSellComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule,ProgressSpinnerModule, FileUploadModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule, EbikeModule, CardModule, AccordionModule],
  exports: [DettagliBikeToSellComponent]
})
export class DettagliBikeToSellModule{
 }