import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BikesForsaleComponent } from './bikes-forsale.component';

describe('BikesForsaleComponent', () => {
  let component: BikesForsaleComponent;
  let fixture: ComponentFixture<BikesForsaleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BikesForsaleComponent]
    });
    fixture = TestBed.createComponent(BikesForsaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
