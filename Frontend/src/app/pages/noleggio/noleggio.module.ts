import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoleggioComponent } from './noleggio.component';
import { ButtonModule } from 'primeng/button';
import { FooterModule } from 'src/app/modules/footer/footer.module';
import { HeaderModule } from 'src/app/modules/header/header.module';
import { EbikeModule } from 'src/app/modules/ebike/ebike.module';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  declarations: [NoleggioComponent],
  imports: [CommonModule, ButtonModule, FooterModule, HeaderModule,EbikeModule,AppRoutingModule,AutoCompleteModule,BrowserAnimationsModule,],
  exports: [NoleggioComponent]
})
export class NoleggioModule { }
