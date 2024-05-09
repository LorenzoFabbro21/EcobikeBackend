import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BiciclettaNoleggioComponent } from './bicicletta-noleggio.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FileUploadModule } from 'primeng/fileupload';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { EbikeModule } from 'src/app/modules/ebike/ebike.module';
import { CardModule } from 'primeng/card';
import { AccordionModule } from 'primeng/accordion';
import { CalendarModule } from 'primeng/calendar';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ErrorDialogModule } from 'src/app/modules/error-dialog/error-dialog.module';
@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [BiciclettaNoleggioComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule, FileUploadModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule,EbikeModule, CardModule, AccordionModule,CalendarModule, ProgressSpinnerModule, ErrorDialogModule],
  exports: [BiciclettaNoleggioComponent]
})
export class BiciclettaNoleggioModule {}
