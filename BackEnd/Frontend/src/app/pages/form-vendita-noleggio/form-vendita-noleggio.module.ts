import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormVenditaNoleggioComponent } from './form-vendita-noleggio.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FileUploadModule } from 'primeng/fileupload';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [FormVenditaNoleggioComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule, FileUploadModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule ],
  exports: [FormVenditaNoleggioComponent]
})
export class FormVenditaNoleggioModule {
 }
