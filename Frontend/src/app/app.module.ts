import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderModule } from './modules/header/header.module';
import { FooterModule } from './modules/footer/footer.module';
import { NoleggioModule } from './pages/noleggio/noleggio.module';
import { HomePageModule } from './pages/home-page/home-page.module';
import { VenditaModule } from './pages/vendita/vendita.module';
import { FormNoleggioModule } from './pages/form-noleggio/form-noleggio.module';
import { FormVenditaModule } from './pages/form-vendita/form-vendita.module'; 
import { BiciclettaDettagliModule } from './pages/bicicletta-dettagli/bicicletta-dettagli.module';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HeaderModule,
    FooterModule,
    NoleggioModule,
    HomePageModule,
    VenditaModule,
    FormNoleggioModule,
    FormVenditaModule,
    BiciclettaDettagliModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
