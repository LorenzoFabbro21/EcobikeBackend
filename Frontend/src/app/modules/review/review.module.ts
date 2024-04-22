import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReviewComponent } from './review.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RatingModule } from 'primeng/rating';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [ReviewComponent],
  imports: [CommonModule, ButtonModule,CardModule,AppRoutingModule, RatingModule, BrowserAnimationsModule, BrowserModule, FormsModule, ReactiveFormsModule],
  exports: [ReviewComponent]
})
export class ReviewModule { }