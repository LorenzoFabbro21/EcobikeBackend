import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormVenditaComponent } from './form-vendita.component';
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
  declarations: [FormVenditaComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule, FileUploadModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule ],
  exports: [FormVenditaComponent]
})
export class FormVenditaModule {
 }
