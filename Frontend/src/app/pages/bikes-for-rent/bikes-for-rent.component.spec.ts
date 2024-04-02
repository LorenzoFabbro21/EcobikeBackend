import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BikesForRentComponent } from './bikes-for-rent.component';

describe('BikesForRentComponent', () => {
  let component: BikesForRentComponent;
  let fixture: ComponentFixture<BikesForRentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BikesForRentComponent]
    });
    fixture = TestBed.createComponent(BikesForRentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
