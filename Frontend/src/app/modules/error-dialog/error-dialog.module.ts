import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorDialogComponent } from './error-dialog.component';
import { DialogModule } from 'primeng/dialog';


@NgModule({
  declarations: [ErrorDialogComponent],
  imports: [CommonModule,DialogModule],
  exports: [ErrorDialogComponent]
})
export class ErrorDialogModule { }
