import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShopFormComponent } from './shop-form.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ErrorDialogModule } from 'src/app/modules/error-dialog/error-dialog.module';
import { FileUploadModule } from 'primeng/fileupload';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [ShopFormComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule,ErrorDialogModule,FileUploadModule,ProgressSpinnerModule ],
  exports: [ShopFormComponent]
})
export class ShopFormModule {
 }
