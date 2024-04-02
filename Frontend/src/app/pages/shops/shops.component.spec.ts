import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopsComponent } from './shops.component';

describe('ShopComponent', () => {
  let component: ShopsComponent;
  let fixture: ComponentFixture<ShopsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShopsComponent]
    });
    fixture = TestBed.createComponent(ShopsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
