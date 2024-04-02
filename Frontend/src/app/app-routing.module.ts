import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NoleggioComponent } from './pages/noleggio/noleggio.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { VenditaComponent } from './pages/vendita/vendita.component';
import { FormNoleggioComponent } from './pages/form-noleggio/form-noleggio.component';
import { BiciclettaVenditaComponent } from './pages/bicicletta-vendita/bicicletta-vendita.component';
import { FormVenditaComponent } from './pages/form-vendita/form-vendita.component';
import { BiciclettaNoleggioComponent } from './pages/bicicletta-noleggio/bicicletta-noleggio.component';
import { LoginFormComponent } from './pages/login-form/login-form.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { SignupComponent } from './pages/signup/signup.component';
import { BikesSoldComponent } from './pages/bikes-sold/bikes-sold.component';
import { PersonalAreaComponent } from './pages/personal-area/personal-area.component';
import { ShopFormComponent } from './pages/shop-form/shop-form.component';
import { BikesForsaleComponent } from './pages/bikes-forsale/bikes-forsale.component';
import { DettagliBikeToSellComponent } from './pages/dettagli-bike-to-sell/dettagli-bike-to-sell.component';
import { BikesForRentComponent } from './pages/bikes-for-rent/bikes-for-rent.component';
import { DetailsBikeToRentComponent } from './pages/details-bike-to-rent/details-bike-to-rent.component';
import { ShopsComponent } from './pages/shops/shops.component';
import { BikesRentedComponent } from './pages/bikes-rented/bikes-rented.component';
import { DetailsShopComponent } from './pages/details-shop/details-shop.component';
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
  },
  {
    title: "login-form",
    path: 'login-form',
    component: LoginFormComponent
  },
  {
    title: "authentication",
    path: 'authentication',
    component: AuthenticationComponent
  },
  {
    title: "signup",
    path: 'signup',
    component: SignupComponent
  },
  {
    title: "bikes-sold",
    path: 'bikes-sold',
    component: BikesSoldComponent
  },
  {
    title: "personal-area",
    path: 'personal_area',
    component: PersonalAreaComponent
  },
  {
    title: "bikes-forsale",
    path: 'bikes_forSale',
    component: BikesForsaleComponent
  },
  {
    title: "shop-form",
    path: 'new_shop',
    component: ShopFormComponent
  },
  {
    title: "dettagli-bike-to-sell",
    path: 'dettagli-bike-to-sell',
    component: DettagliBikeToSellComponent
  },
  {
    title: "bikes-forRent",
    path: 'bikes_forRent',
    component: BikesForRentComponent
  },
  {
    title: "details-bike-to-rent",
    path: 'details-bike-to-rent',
    component: DetailsBikeToRentComponent
  },
  {
    title: "shops",
    path: 'shops',
    component: ShopsComponent
  },
  {
    title: 'bikes_rented',
    path: 'bikes_rented',
    component: BikesRentedComponent
  },
  {
    title: "details-shop",
    path: 'details-shop',
    component: DetailsShopComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
