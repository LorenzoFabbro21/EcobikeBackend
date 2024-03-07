import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NoleggioComponent } from './pages/noleggio/noleggio.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { VenditaComponent } from './pages/vendita/vendita.component';
import { FormNoleggioComponent } from './pages/form-noleggio/form-noleggio.component';
import { BiciclettaVenditaComponent } from './pages/bicicletta-vendita/bicicletta-vendita.component';
import { FormVenditaComponent } from './pages/form-vendita/form-vendita.component';
import { BiciclettaNoleggioComponent } from './pages/bicicletta-noleggio/bicicletta-noleggio.component';

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
    title: "dettaglio_vendita",
    path: 'dettagli_vendita',
    component: BiciclettaVenditaComponent
  },
  {
    title: "dettaglio_noleggio",
    path: 'dettagli_noleggio',
    component: BiciclettaNoleggioComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
