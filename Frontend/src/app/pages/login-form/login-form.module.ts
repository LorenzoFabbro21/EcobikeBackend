import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FileUploadModule } from 'primeng/fileupload';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ErrorDialogModule } from 'src/app/modules/error-dialog/error-dialog.module';
@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [LoginFormComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, AutoCompleteModule,ProgressSpinnerModule, FileUploadModule, BrowserAnimationsModule,HttpClientModule,InputTextModule,FormsModule,ErrorDialogModule ],
  exports: [LoginFormComponent]
})
export class LoginFormModule {
 }
