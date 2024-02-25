import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

@NgModule({
  declarations: [FooterComponent],
  imports: [CommonModule, ButtonModule,InputTextModule],
  exports: [FooterComponent]
})
export class FooterModule { }
