import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Bicicletta } from 'src/app/interfaces/bicicletta';
import { Shop } from 'src/app/interfaces/shop';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';
import { UserLoggedService } from 'src/app/services/user-logged.service';
import { Review } from 'src/app/interfaces/review';
import { LoggedUser, User } from 'src/app/classes/user';
import { adRent } from 'src/app/interfaces/adRent';
import { adSell } from 'src/app/interfaces/adSell';
import { bikeRentSell } from 'src/app/interfaces/bikeRentSell';
import { HttpErrorResponse } from '@angular/common/http';

interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}

export interface ReviewsUser {
  review: Review;
  user: User;
}

@Component({
  selector: 'app-details-shop',
  templateUrl: './details-shop.component.html',
  styleUrls: ['./details-shop.component.scss']
})
export class DetailsShopComponent {

 
  bikeRentPrice: bikeRentSell[] = []
  bikeSellPrice: bikeRentSell[] = []
  rent: adRent[] = []
  sell: adSell[] = []
  shop!: Shop;
  id: number = 0;
  bikestorent: Bicicletta[]=[];
  torentNull : boolean = false;
  tosellNull: boolean = false;
  bikestosell: Bicicletta[]=[];
  idUser: number = 0;
  mostraSpinner:boolean = true;
  text! : string;
  score! : number;
  reviews: Review[]=[];
  userLogged: LoggedUser | null = null;
  first: number = 0;
  rows: number = 3;
  reviewUser: ReviewsUser[]=[];
  pagedReviewUser: ReviewsUser[] = [];

  showError : boolean = false;
  errorStatus: string = "";
  errorMessage: string = "";

    constructor ( private route: ActivatedRoute, private ebService: EcobikeApiService, private userService: UserLoggedService) {
      this.userLogged = userService.userLogged
  }
  ngOnInit(): void {
    window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
    this.route.queryParams.subscribe(params => {
      this.id = JSON.parse(params['idShop']);
      this.idUser = JSON.parse(params['idUser']);
    });
   
      
      this.ebService.get_shop(this.id).subscribe ({
        next: (response: Shop) => {
          if (response != null)
            this.shop = response
        }
      })

      this.ebService.list_bikes_forRent_by_user(this.idUser).subscribe ({
        next: (response: Bicicletta[]) => {
          if(response != null) {
            if((response.length != 0 )){
              this.bikestorent = response;
              this.ebService.elenco_noleggi().subscribe ({
                next: (response: adRent[]) => {
                  if(response != null){
                    this.rent = response;
                    this.rent.forEach(rent => {
                      this.bikestorent.forEach(bike => {
                        if(rent.idBike == bike.id) {
                          const obj: bikeRentSell= {
                            bike: bike,
                            price: rent.price ? rent.price : 0
                          };
                          this.bikeRentPrice.push(obj);
                        }
                      });
                    });
                  }
                }
              })
              this.mostraSpinner = false;
            } else {
              this.mostraSpinner = false;
              this.torentNull = true;
            }
          }else {
            this.mostraSpinner = false;
            this.torentNull = true;
          }
        }
      });
      this.ebService.list_bikes_forsale_by_user(this.idUser).subscribe ({
        next: (response: Bicicletta[]) => {
          if(response != null){
            if(response.length != 0 ) {
              this.bikestosell = response;
              this.ebService.elenco_vendite().subscribe ({
                next: (response: adSell[]) => {
                  if(response != null){
                    this.sell = response;
                    this.sell.forEach(sell => {
                      this.bikestosell.forEach(bike => {
                        if(sell.idBike == bike.id) {
                          const obj: bikeRentSell= {
                            bike: bike,
                            price: sell.price ? sell.price : 0
                          };
                          this.bikeSellPrice.push(obj);
                        }
                      });
                    });
                  }
                }
              })
              this.mostraSpinner = false;
            } else {
              this.mostraSpinner = false;
              this.tosellNull = true;
            }
          }else {
            this.mostraSpinner = false;
            this.tosellNull = true;
          }
        }
      })

      this.ebService.list_reviews_fromidShop(this.id).subscribe ({
        next: (listReviews: Review[]) => {
          if(listReviews != null){
            if(listReviews.length !== 0) {
              this.reviews = listReviews;
              const count = this.reviews.length;
              let i = 0;
              this.reviews.forEach(review => {
                this.ebService.getPrivateById(review.idUser).subscribe({
                  next: (response: User) => {
                    if(response != null){
                      i++;
                      const obj: ReviewsUser = 
                      {
                        review: review,
                        user: response
                      }
                      this.reviewUser.push(obj)
                      if ( i == count) {
                        this.loadPagedReviewUser();
                      }
                    }
                  }
                });
              });
              
            }
          }
        }
      });

  
      

  
}
  send(){
    let review: Review;
    if( this.userService.userLogged?.token !== undefined) {
      let token : string = this.userService.userLogged?.token;
      review = {
        score: this.score,
        text: this.text,
        idShop: this.id,
        idUser: this.userService.userLogged.id
      }
      this.ebService.new_review(review, token).subscribe({
        next: (response) => {
          if( response && response.id)
            this.ebService.list_reviews_fromidShop(this.shop.id).subscribe(listreviews => {
              if(listreviews !== null) {
                if(listreviews.length !== 0) {
                  this.reviews = listreviews;
                  this.reviewUser = [];
                  const count = this.reviews.length;
                  let i = 0;
                  this.reviews.forEach(review => {
                    this.ebService.getPrivateById(review.idUser).subscribe({
                      next: (response: User) => {
                        if(response != null){
                          i++;
                          const obj: ReviewsUser = 
                          {
                            review: review,
                            user: response
                          }
                          this.reviewUser.push(obj)
                          if ( i == count) {
                            this.loadPagedReviewUser();
                          }
                        }
                      }
                    });
                  });
                }
              }
            });
          },error: (error: HttpErrorResponse) => {
            if (error.status === 404) {
              this.errorStatus = "Error:" + error.status.toString();
              const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
              if( errorMessageParts.length == 1) {
                this.errorMessage = errorMessageParts[0];
              }
              else {
                const errorMessage = errorMessageParts.slice(1).join(':').trim();
                this.errorMessage = errorMessage;
              }
              
              this.showError = true;
              this.mostraSpinner = false;
            } else if (error.status === 400) {
              this.errorStatus = "Error:" + error.status.toString();
              const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
              if( errorMessageParts.length == 1) {
                this.errorMessage = errorMessageParts[0];
              }
              else {
                const errorMessage = errorMessageParts.slice(1).join(':').trim();
                this.errorMessage = errorMessage;
              }
              this.mostraSpinner = false;
              this.showError = true;
            } else {
              this.errorStatus = "Error:" + error.status.toString();
              const errorMessageParts = error.error.split(':'); // Dividi la stringa utilizzando i due punti
              if( errorMessageParts.length == 1) {
                this.errorMessage = errorMessageParts[0];
              }
              else {
                const errorMessage = errorMessageParts.slice(1).join(':').trim();
                this.errorMessage = errorMessage;
              }
              this.mostraSpinner = false;
              this.showError = true;
            }
          }
        });
      }
    }
     
  onPageChange(event: any) {
    this.first = event.first;
    this.rows = event.rows;
    this.loadPagedReviewUser();
  }
  loadPagedReviewUser() {
    this.pagedReviewUser = this.reviewUser.slice(this.first, this.first + this.rows);
  }
  changeValue (event : boolean | any) :void {
    this.showError = event;
  }

}


