import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsShopComponent } from './details-shop.component';

describe('DetailsShopComponent', () => {
  let component: DetailsShopComponent;
  let fixture: ComponentFixture<DetailsShopComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsShopComponent]
    });
    fixture = TestBed.createComponent(DetailsShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
