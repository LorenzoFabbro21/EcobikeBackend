import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BikesRentedComponent } from './bikes-rented.component';

describe('BikesRentedComponent', () => {
  let component: BikesRentedComponent;
  let fixture: ComponentFixture<BikesRentedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BikesRentedComponent]
    });
    fixture = TestBed.createComponent(BikesRentedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
