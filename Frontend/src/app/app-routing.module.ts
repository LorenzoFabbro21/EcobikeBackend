import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NoleggioComponent } from './pages/noleggio/noleggio.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { VenditaComponent } from './pages/vendita/vendita.component';
import { FormNoleggioComponent } from './pages/form-noleggio/form-noleggio.component';
import { BiciclettaDettagliComponent } from './pages/bicicletta-dettagli/bicicletta-dettagli.component';
import { FormVenditaComponent } from './pages/form-vendita/form-vendita.component';

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
    title: "form_noleggio",
    path: 'form_noleggio',
    component: FormNoleggioComponent
  },
  {
    title: "form_vendita",
    path: 'form_vendita',
    component: FormVenditaComponent
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
