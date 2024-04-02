import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsBikeToRentComponent } from './details-bike-to-rent.component';

describe('DetailsBikeToRentComponent', () => {
  let component: DetailsBikeToRentComponent;
  let fixture: ComponentFixture<DetailsBikeToRentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsBikeToRentComponent]
    });
    fixture = TestBed.createComponent(DetailsBikeToRentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
