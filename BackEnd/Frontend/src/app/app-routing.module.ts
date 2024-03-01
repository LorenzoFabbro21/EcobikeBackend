import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NoleggioComponent } from './pages/noleggio/noleggio.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { VenditaComponent } from './pages/vendita/vendita.component';
import { FormVenditaNoleggioComponent } from './pages/form-vendita-noleggio/form-vendita-noleggio.component';
import { BiciclettaDettagliComponent } from './pages/bicicletta-dettagli/bicicletta-dettagli.component';

const routes: Routes = [
  {
    title: '',
    path: '',
    component: HomePageComponent
  }, 
  {
    title: "Noleggio",
    path: 'noleggio',
    component: NoleggioComponent
  },
  {
    title: "Vendita",
    path: 'vendita',
    component: VenditaComponent
  }, 
  {
    title: "Form_inserimento",
    path: 'form_inserimento',
    component: FormVenditaNoleggioComponent
  },
  {
    title: "dettaglio",
    path: 'dettagli_ebike',
    component: BiciclettaDettagliComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
