import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/classes/user';
import { Review } from 'src/app/interfaces/review';
import { ReviewsUser } from 'src/app/pages/details-shop/details-shop.component';
import { EcobikeApiService } from 'src/app/services/ecobike-api.service';



@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit{
  user!: User
  image = 'assets/images/user.jpg'
  value: number = 0;
  @Input()
    reviewUser!: ReviewsUser;

  constructor() {
  }
  ngOnInit(): void {
    this.value = this.reviewUser.review.score;
  }
  
}
