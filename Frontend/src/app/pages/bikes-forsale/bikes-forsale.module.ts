import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BikesForsaleComponent } from './bikes-forsale.component';
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
@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [BikesForsaleComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule,ProgressSpinnerModule, FileUploadModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule, EbikeModule],
  exports: [BikesForsaleComponent]
})
export class BikesForsaleModule {
 }
