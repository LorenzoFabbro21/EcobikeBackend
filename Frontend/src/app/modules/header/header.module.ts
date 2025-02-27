import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header.component';
import { ButtonModule } from 'primeng/button';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MenubarModule } from 'primeng/menubar';

@NgModule({
  declarations: [HeaderComponent],
  imports: [CommonModule, ButtonModule, AppRoutingModule, MenubarModule],
  exports: [HeaderComponent]
})
export class HeaderModule { }
