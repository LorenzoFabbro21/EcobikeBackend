import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import {  Router } from '@angular/router';
import { LoggedUser } from 'src/app/classes/user';
import { UserLoggedService } from 'src/app/services/user-logged.service';
import { MenuItem  } from 'primeng/api';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  items: MenuItem[] | undefined;
  filteredItems: MenuItem[]| undefined = undefined;
  userName?: string;





  ngOnChanges (changes: SimpleChanges): void {

    if ( changes['sidebar']) {
      if ( changes['sidebar'].currentValue === false && changes['sidebar'].firstChange !== true) {
        const hamburgerBtn = document.querySelector('.hamburger-button');
        hamburgerBtn?.classList.toggle('active');
      }
    }


    
  }

  @Output()
    clickButton = new EventEmitter<boolean>();

  userLogged: LoggedUser | null = null;
  constructor ( private router: Router, private userService: UserLoggedService ) {
    
    
    this.userLogged = this.userService.bindUpdateUser((updatedUser) => {
      this.userLogged = updatedUser;
      if ( this.items) {
        this.items[0].label = this.userLogged?.name;
        this.filteredItems = this.filterMenuItems(this.items);
      }
      
    });
  }


  ngOnInit() {
    this.items = [
      {
        label: this.userLogged?.name,
        icon: 'pi pi-fw pi-user',
        class: 'menubar',
        items: [
          {
            label: 'E-bike in vendita',
            icon: 'pi pi-fw pi-filter',
            routerLink: 'bikes_forSale'
          },
          {
            icon: 'pi pi-fw pi-bars',
            label: 'E-bike in noleggio',
            routerLink: 'bikes_forRent'
          },
          {
            label: 'E-bike vendute',
            icon: 'pi pi-fw pi-users',
            routerLink: 'bikes-sold'
          },
          {
            label: 'E-bike noleggiate',
            icon: 'pi pi-fw pi-users',
            routerLink: 'bikes_rented'
          },
          {
            separator: true
          },
          {
            label: 'Informazioni personali',
            icon: 'pi pi-fw pi-users',
            routerLink: 'personal_area'
          },
          {
            label: 'personal rent',
            icon: 'pi pi-fw pi-users',
            routerLink: 'personal-rent'
          },
          {
            label: 'personal buy',
            icon: 'pi pi-fw pi-users',
            routerLink: 'personal-buy'
          }
        ]
      }
    ];
    if ( this.items[0].items) {
      this.filteredItems = this.items;
      this.filteredItems = this.filterMenuItems(this.items);
      // Assegna le voci del menu filtrate al modello del menu
    }
}

filterMenuItems(items: MenuItem[]): any[] {
  // Se this.userLogged è di tipo 'p', rimuovi la voce 'E-bike in noleggio'
  const obj = items[0].items;
  let array = items;
  if ( obj !== undefined && this.userLogged?.type == 'p') {
    let obj2 = obj.map(item => {
      if (item.separator) {
        return item;
      }
      if (item.label !== 'E-bike in noleggio' && item.label !== 'E-bike noleggiate') {
        return item;
      }
      return null; // Aggiungi il caso in cui l'elemento potrebbe essere null
    }).filter(item => item !== null) as MenuItem[];
    array[0].items = obj2;
    return array;
  }
  else if (this.userLogged?.type == 'd') {
    return items;
  }
  return [];

}



  logout (): void {
    this.userService.logout();
    this.router.navigate(['/']);

  }

  toggleMenu ():void {
    const hamburgerBtn = document.querySelector('.hamburger-button');
    hamburgerBtn?.classList.toggle('active');
    this.clickButton.emit(!this.sidebar);
  }

  @Input()
    sidebar?:boolean;

  page = "";

}
