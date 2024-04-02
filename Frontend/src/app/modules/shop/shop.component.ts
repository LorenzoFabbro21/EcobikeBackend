import { AfterViewInit, Component, Input } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { Shop } from 'src/app/interfaces/shop';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent {
  
  @Input()
    shop?: Shop

  constructor ( private router: Router) {
    
  }
  clickShop() {
    const navigationExtras: NavigationExtras = {
      queryParams:{
        idShop: this.shop?.id,
        idUser: this.shop?.idUser
      }
    };
    this.router.navigate(['/details-shop'], navigationExtras);
  }
}
