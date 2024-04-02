import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BikesSoldComponent } from './bikes-sold.component';

describe('BikesSoldComponent', () => {
  let component: BikesSoldComponent;
  let fixture: ComponentFixture<BikesSoldComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BikesSoldComponent]
    });
    fixture = TestBed.createComponent(BikesSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
